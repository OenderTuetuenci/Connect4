package fileIoComponent

import Connect4.gridComponent.{Grid, GridInterface}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.{Post, Get}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{JsObject, Json}

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class HTTPRequestHandler{
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  def getJson:String = {
    val response = Http().singleRequest(Get("http://localhost:8080/model/grid"))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def load(json:String): Unit = {
    val response = Http().singleRequest(Post("http://localhost:8080/model/grid/rebuild?json="+json))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }
  def rebuildGrid(tmp:String):GridInterface ={
    var grid:GridInterface = Grid()
    val json = Json.parse(tmp)
    for(index <- 0 to 41){
      val idx = (json \\ "index")(index).as[Int]
      val value = (json \\ "val")(index).as[Int]
      grid = grid.set(idx,value)
    }
    grid
  }
}
