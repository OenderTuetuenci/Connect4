import Connect4.model.fileIoComponent.FileIOInterface
import Connect4.model.gridComponent.{Grid, GridInterface}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import play.api.libs.json._

import scala.io.{Source, StdIn}
import Connect4.model.fileIoComponent.fileIoJsonImpl.FileIo
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

object ModelManager extends PlayJsonSupport{

  def gridToJson(grid: GridInterface):JsObject ={
    Json.obj(
      "grid"->Json.obj(
        "cells"->Json.toJson( for{
          index <- 0 until 42
        }yield{
          Json.obj(
            "index"->index,
            "val"->grid.grid(index)
          )
        })
      )
    )
  }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    var grid:GridInterface = Grid()
    val fileIo:FileIOInterface = new FileIo()

    val route = concat(
      path("model" / "grid") {
        get {
          complete(gridToJson(grid))
        }
      },
      path("model" / "grid" / "toString"){
        get {
          complete(grid.toString)
        }
      },
      path("model" / "grid" / "put") {
        parameters("column".optional,"player".optional) { (column,player) => {
          val column2 = column.getOrElse("")
          val player2 = player.getOrElse("")
            if(column2 == "" || player2 == "") {
              complete(StatusCodes.BadRequest)
            } else{
              grid.put(column2.toInt,player2.toInt) match {
                case Some(value) => grid = value._2
                  complete(value._1)
                case None => complete(69)
              }
            }
          }
        }
      },
      path("model" / "grid" / "set"){
        parameters("i".optional,"value".optional) { (i, value) =>
          val i2 = i.getOrElse("")
          val value2 = value.getOrElse("")
          if(i2 == "" || value2 == "") {
            complete(StatusCodes.BadRequest)
          } else {
            grid = grid.set(i2.toInt,value2.toInt)
            complete(StatusCodes.OK)
          }
        }
      },
      path("model" / "fileIo" / "save"){
        parameters("player".optional) { (player) =>
          val player2 = player.getOrElse("")
          if (player2 == "") {
            complete(StatusCodes.BadRequest)
          } else {
            fileIo.save(grid,player2.toInt)
            complete(StatusCodes.OK)
          }
        }
      },
      path("model" / "fileIo" / "load") {
        val stats = fileIo.load
        grid = stats._1
        complete(stats._2)
      },
      path("model" / "grid" / "checkConnect") {
        parameters("player".optional) { (player) =>
          val player2 = player.getOrElse("")
          println(grid.checkConnect4(player2.toInt))
          if (player2 == "") {
            complete(StatusCodes.BadRequest)
          } else {
            complete(grid.checkConnect4(player2.toInt))
          }
        }
      })

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

  }
}
