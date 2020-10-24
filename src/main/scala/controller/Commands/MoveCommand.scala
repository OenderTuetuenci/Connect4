package controller.Commands

import controller.Controller
import utils.Command


class MoveCommand(column:Int,player:Int, controller:Controller) extends Command{
  var stone:(Int,Int) = (-1,-1)
  override def doStep(): Unit = {
    stone = controller.grid.put(column, player)
    var end: Boolean = false
    if(stone.equals((-1,-1)))
      controller.notifyObservers("blockedColumn")
    else {
      end = controller.checkForWinner(stone)
      if(!end) {
        controller.nextPlayer()
        controller.notifyObservers("updateGrid")
      }
      else
        controller.notifyObservers("endGame")
    }
  }
  override def undoStep(): Unit = {
    controller.grid.resetValue(stone._1,stone._2)
    controller.resetPlayer(player)
    controller.notifyObservers("updateGrid")
  }
  override def redoStep(): Unit = {
    stone = controller.grid.put(column, player)
    var end: Boolean = false
    if(stone.equals((-1,-1)))
      controller.notifyObservers("blockedColumn")
    else {
      end = controller.checkForWinner(stone)
      if(!end) {
        controller.nextPlayer()
        controller.notifyObservers("updateGrid")
      }
      else
        controller.notifyObservers("endGame")
    }
  }
}
