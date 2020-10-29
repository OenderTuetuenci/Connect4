package Connect4.controller.controllerComponent

import com.google.inject.Inject
import Connect4.controller.Commands.MoveCommand
import Connect4.controller.{saveGameEvent, startGameEvent, updateAllGridEvent, updateGridEvent}
import Connect4.model.fileIoComponent.FileIOInterface
import Connect4.model.gridComponent.GridInterface
import Connect4.utils.UndoManager

class Controller @Inject() (var grid:GridInterface,var fileIo:FileIOInterface) extends ControllerInterface {
  var players: List[Int] = 1::2::Nil
  var winner:Int = 0
  val undoManager = new UndoManager

  def getGridString :String = grid.toString
  def startGame() : Unit = publish(new startGameEvent)
  def save():Unit = {
    fileIo.save(grid,players.head)
    publish(new saveGameEvent)
  }
  def load():Unit ={
    val stats = fileIo.load
    grid=stats._1
    resetPlayer(stats._2)
    publish(new updateAllGridEvent)
  }
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
