package Connect4.controller.Commands

import Connect4.controller.controllerComponent.{Controller, ControllerInterface}
import Connect4.controller.{blockedColumnEvent, endGameEvent, updateGridEvent}
import Connect4.utils.Command


class MoveCommand(column:Int, player:Int, controller:ControllerInterface) extends Command{
  var index:Int = -1
  override def doStep(): Unit = {
    val temp = controller.grid.put(column,player)
    temp._1 match {
      case Some(value) => { controller.grid = temp._2
                            index = value
                            update()}
      case None => controller.publish(new blockedColumnEvent)
    }
  }
  override def undoStep(): Unit = {
    controller.grid = controller.grid.set(index,0)
    controller.setPlayer(player)
    controller.publish(updateGridEvent(index,0))
  }
  override def redoStep(): Unit = {
    controller.grid = controller.grid.set(index,player)
    update()
  }
  private def update(): Unit ={
    var end: Boolean = false
    end = controller.checkForWinner()
    if(!end){
      controller.nextPlayer()
      controller.publish(updateGridEvent(index,player))
    }else {
      controller.publish(updateGridEvent(index,player))
      controller.publish(new endGameEvent)
    }
  }
}
