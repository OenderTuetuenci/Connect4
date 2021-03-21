package Connect4.controller.Commands

import Connect4.controller.controllerComponent.{Controller, ControllerInterface}
import Connect4.controller.{blockedColumnEvent, endGameEvent, updateGridEvent}
import Connect4.utils.Command


class MoveCommand(column:Int,player:Int, controller:ControllerInterface) extends Command{
  var index:Int = -1
  override def doStep(): Unit = {
    var temp = controller.grid.put(column, player)
    temp match {
      case Some(value) => index = value
      case None => controller.publish(new blockedColumnEvent)
    }
    update()
  }
  override def undoStep(): Unit = {
    controller.grid.set(index,0)
    controller.resetPlayer(player)
    controller.publish(updateGridEvent(index,0))
  }
  override def redoStep(): Unit = {
    controller.grid.set(index,player)
    update()
  }
  private def update(): Unit ={
    var end: Boolean = false
    end = controller.checkForWinner()
    if(!end){
      controller.nextPlayer()
      controller.publish(updateGridEvent(index,player))
    }else
      controller.publish(new endGameEvent)
  }
}
