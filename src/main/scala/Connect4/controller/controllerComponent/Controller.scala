package Connect4.controller.controllerComponent

import com.google.inject.Inject
import Connect4.controller.Commands.MoveCommand
import Connect4.controller.{saveGameEvent, startGameEvent, updateAllGridEvent, updateGridEvent}
import Connect4.gridComponent.GridInterface
import Connect4.utils.UndoManager
import fileIoComponent.FileIOInterface

import scala.util.{Failure, Success, Try}

class Controller @Inject() (var grid:GridInterface,var fileIo:FileIOInterface) extends ControllerInterface {
  var players: List[Int] = 1::2::Nil
  var winner:Int = 0
  val undoManager = new UndoManager

  def startGame() : Unit = publish(new startGameEvent)
  def save():Unit = {
    fileIo.save(grid,players.head)
    publish(new saveGameEvent)
  }
  def load():Unit ={
    val stats = fileIo.load
    grid=stats._1
    setPlayer(stats._2)
    publish(new updateAllGridEvent)
  }
  def move(column: String): Try[Unit] = {
    if (column.toIntOption.isDefined){
      Try(undoManager.doStep(new MoveCommand(column.toInt,players.head,this)))
    } else {
      Failure(new Exception("Input is not a valid Input (s, l, u, r, q, 0 - 6)\n"))
    }
  }

  def undo():Unit= undoManager.undoStep()
  def redo():Unit= undoManager.redoStep()

  def checkForWinner(): Boolean = grid.checkConnect4(players.head)
  def nextPlayer():Unit = players = players.tail ::: List(players.head)
  def setPlayer(player:Int):Unit={
    if(player == 1)
      players = 1::2::Nil
    else
      players = 2::1::Nil
  }
}