package Connect4.controller

import Connect4.controller.utils.UndoManager
import Connect4.model.gridComponent.GridInterface

import scala.swing.Publisher
import scala.util.Try

trait ControllerInterface extends Publisher {
  val undoManager: UndoManager
  var players: List[Int]
  var winner: Int
  var grid: GridInterface


  def startGame(): Unit

  def move(column: String): Try[Unit]

  def undo(): Unit

  def redo(): Unit

  def checkForWinner(): Boolean

  def nextPlayer(): Unit

  def setPlayer(player: Int): Unit

  def save(): Unit

  def load(): Unit
}
