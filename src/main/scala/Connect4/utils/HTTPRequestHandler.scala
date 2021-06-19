package Connect4.utils

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.{Get, Post}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.JsObject

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class HTTPRequestHandler extends PlayJsonSupport{
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  def checkWinner(player:Int): Boolean = {
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/checkConnect?player=" + player))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Boolean])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def loadGame(): Int = {
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/load"))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def saveGame():Int = {
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/saveDB"))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def doStepMove(column:Int,player:Int): Int = {
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/put?column=" + column + "&player=" +player))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def undoStepMove(index:Int): Int = {
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/set?i="+index +"&value="+0))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def redoStepMove(index:Int, player:Int): Int ={
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/set?i="+index +"&value="+player))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def updateAllGridGui(): JsObject = {
    val response = Http().singleRequest(Get("http://0.0.0.0:8080/model/grid"))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[JsObject])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def showGridTui(): String = {
    val response = Http().singleRequest(Post("http://0.0.0.0:8080/model/grid/toString"))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }
}
