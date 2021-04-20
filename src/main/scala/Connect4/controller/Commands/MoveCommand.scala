package Connect4.controller.Commands

import Connect4.controller.controllerComponent.{Controller, ControllerInterface}
import Connect4.controller.{blockedColumnEvent, endGameEvent, updateGridEvent}
import Connect4.utils.{Command, HTTPRequestHandler}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration


class MoveCommand(column:Int, player:Int, controller:ControllerInterface) extends Command with PlayJsonSupport{
  var index:Int = -1

  override def doStep(): Unit = {
    index = controller.requestHandler.doStepMove(column,player)
    if(index == 69)
      controller.publish(new blockedColumnEvent)
    else
      update()
  }
  override def undoStep(): Unit = {
    val tmp = controller.requestHandler.undoStepMove(index)
    controller.setPlayer(player)
    controller.publish(updateGridEvent(index,0))
  }
  override def redoStep(): Unit = {
    val tmp = controller.requestHandler.redoStepMove(index, player)
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
