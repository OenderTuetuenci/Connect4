package Connect4.controller.Commands

import Connect4.controller.utils.Command
import Connect4.controller.{ControllerInterface, blockedColumnEvent, endGameEvent, updateGridEvent}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.Json

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success}


class MoveCommand(column:Int, player:Int, controller:ControllerInterface) extends Command with PlayJsonSupport{
  var index:Int = -1
  override def doStep(): Unit = {
    index = {
      val response = Http().singleRequest(Post("http://localhost:8080/model/grid/put?column=" + column + "&player=" +player))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
      Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
    }
    if(index == 69)
      controller.publish(new blockedColumnEvent)
    else
      update()
  }
  override def undoStep(): Unit = {
    val tmp = {
      val response = Http().singleRequest(Post("http://localhost:8080/model/grid/set?i="+index +"&value="+0))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
      Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
    }
    controller.setPlayer(player)
    controller.publish(updateGridEvent(index,0))
  }
  override def redoStep(): Unit = {
    val tmp = {
      val response = Http().singleRequest(Post("http://localhost:8080/model/grid/set?i="+index +"&value="+player))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
      Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
    }
    update()
  }
  private def update(): Unit ={
    val end: Boolean = controller.checkForWinner()
    if(!end){
      controller.nextPlayer()
      controller.publish(updateGridEvent(index,player))
    } else {
      controller.publish(updateGridEvent(index,player))
      controller.publish(new endGameEvent)
    }
  }
}
