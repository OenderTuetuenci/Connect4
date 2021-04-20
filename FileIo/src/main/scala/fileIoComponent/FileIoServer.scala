package fileIoComponent

import Connect4.gridComponent.{Grid, GridInterface}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import fileIoComponent.fileIoJsonImpl.FileIo
import play.api.libs.json._

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

object FileIoServer extends PlayJsonSupport{

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext


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
    val injector: Injector = Guice.createInjector(new FileIoModule)
    val fileIo:FileIOInterface = injector.getInstance(classOf[FileIOInterface])
    val requestHandler = new HTTPRequestHandler()

    val route = concat (
      path("fileIo" / "save"){
        parameters("player".optional) { player =>
          val player2 = player.getOrElse("")
          if (player2 == "") {
            complete(69)
          } else {
            val json = requestHandler.getJson
            val grid = requestHandler.rebuildGrid(json)
            fileIo.save(grid,player2.toInt)
            complete(420)
          }
        }
      },
      path("fileIo" / "load") {
        post{
          val stats = fileIo.load
          val json = gridToJson(stats._1)
          val result = requestHandler.load(json.toString())
          complete(stats._2)
        }
      },
    )

    val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)

    println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
