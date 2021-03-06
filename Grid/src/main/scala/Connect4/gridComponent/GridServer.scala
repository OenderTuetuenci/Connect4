package Connect4.gridComponent

import Connect4.gridComponent.DBComponent.DAO
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.google.inject.{Guice, Injector}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json._

import scala.io.StdIn

object GridServer extends PlayJsonSupport {
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  def main(args: Array[String]): Unit = {
    val injector: Injector = Guice.createInjector(new GridModule)
    var grid: GridInterface = injector.getInstance(classOf[GridInterface])
    val dataBase: DAO = injector.getInstance(classOf[DAO])
    dataBase.create()
    val route = concat(
      path("model" / "grid") {
        get {
          complete(gridToJson(grid))
        }
      },
      path("model" / "grid" / "toString") {
        post {
          complete(grid.toString)
        }
      },
      path("model" / "grid" / "put") {
        parameters("column".optional, "player".optional) { (column, player) => {
          val column2 = column.getOrElse("")
          val player2 = player.getOrElse("")
          if (column2 == "" || player2 == "") {
            complete(69)
          } else {
            grid.put(column2.toInt, player2.toInt) match {
              case Some(value) => grid = value._2
                complete(value._1)
              case None => complete(69)
            }
          }
        }
        }
      },
      path("model" / "grid" / "set") {
        parameters("i".optional, "value".optional) { (i, value) =>
          val i2 = i.getOrElse("")
          val value2 = value.getOrElse("")
          if (i2 == "" || value2 == "") {
            complete(69)
          } else {
            grid = grid.set(i2.toInt, value2.toInt)
            complete(420)
          }
        }
      },
      path("model" / "grid" / "checkConnect") {
        parameters("player".optional) { player =>
          val player2 = player.getOrElse("")
          if (player2 == "") {
            complete(69)
          } else {
            complete(grid.checkConnect4(player2.toInt))
          }
        }
      },
      path("model" / "grid" / "rebuild") {
        parameters("json".optional) { json =>
          val json2 = json.getOrElse("")
          if (json2 == "") {
            complete(69)
          } else {
            grid = grid.rebuild(json2)
            complete(420)
          }
        }
      },
      path("model" / "grid" /"saveDB"){
        post{
          dataBase.update(grid)
          complete(420)
        }
      },
      path("model" / "grid" /"load"){
        post{
          grid = dataBase.read()
          complete(420)
        }
      },
      path("model"/ "grid" / "newgame"){
        get{
          grid = injector.getInstance(classOf[GridInterface])
          complete(420)
        }
      }
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(route)

    println(s"Server online at http://0.0.0.0:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

  }

  def gridToJson(grid: GridInterface): JsObject = {
    Json.obj(
      "grid" -> Json.obj(
        "cells" -> Json.toJson(for {
          index <- 0 until 42
        } yield {
          Json.obj(
            "index" -> index,
            "val" -> grid.grid(index)
          )
        })
      )
    )
  }
}
