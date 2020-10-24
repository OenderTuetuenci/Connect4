package controller.Commands

import controller.{Controller, blockedColumnEvent, endGameEvent, updateGridEvent}
import utils.Command


class MoveCommand(column:Int,player:Int, controller:Controller) extends Command{
  var stone:(Int,Int) = (-1,-1)
  override def doStep(): Unit = {
    stone = controller.grid.put(column, player)
    var end: Boolean = false
    if(stone.equals((-1,-1)))
      controller.publish(new blockedColumnEvent)
    else {
      controller.publish(updateGridEvent(stone,player))
      end = controller.checkForWinner(stone)
      if(!end) {
        controller.nextPlayer()
      }
      else
        controller.publish(new endGameEvent)
    }
  }
  override def undoStep(): Unit = {
    controller.grid.resetValue(stone._1,stone._2)
    controller.resetPlayer(player)
    controller.publish(updateGridEvent(stone,0))
  }
  override def redoStep(): Unit = {
    stone = controller.grid.put(column, player)
    var end: Boolean = false
    if(stone.equals((-1,-1)))
      controller.publish(new blockedColumnEvent)
    else {
      controller.publish(updateGridEvent(stone,player))
      end = controller.checkForWinner(stone)
      if(!end) {
        controller.nextPlayer()
      }
      else
        controller.publish(new endGameEvent)
    }
  }
}
