package Connect4.controller.Commands

import Connect4.controller.utils.Command
import Connect4.controller.{ControllerInterface, blockedColumnEvent, endGameEvent, updateGridEvent}


class MoveCommand(column:Int, player:Int, controller:ControllerInterface) extends Command{
  var index:Int = -1
  override def doStep(): Unit = {
    controller.grid.put(column,player) match {
      case Some(value) => { controller.grid = value._2
                            index = value._1
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
    } else {
      controller.publish(updateGridEvent(index,player))
      controller.publish(new endGameEvent)
    }
  }
}
