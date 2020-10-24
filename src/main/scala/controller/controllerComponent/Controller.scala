package controller.controllerComponent

import com.google.inject.Inject
import controller.Commands.MoveCommand
import controller.{ControllerInterface, startGameEvent}
import model.GridInterface
import utils.UndoManager

class Controller @Inject() (var grid:GridInterface) extends ControllerInterface {
  var players: List[Int] = 1::2::Nil
  var winner:Int = 0
  val undoManager = new UndoManager

  def getGridString :String = grid.toString
  def startGame() : Unit = publish(new startGameEvent)

  def move(column: Int): Unit = {
    undoManager.doStep(new MoveCommand(column,players.head,this))
  }
  def undo():Unit={
    undoManager.undoStep()
  }
  def redo():Unit={
    undoManager.redoStep()
  }

  def checkForWinner(stone:(Int,Int)): Boolean ={
    grid.checkConnect4(stone,players.head)
  }
  def nextPlayer():Unit = players = players.tail ::: List(players.head)
  def resetPlayer(player:Int):Unit={
    if(player == 1)
      players = 1::2::Nil
    else
      players = 2::1::Nil
  }
}
