package Connect4.controller.controllerComponent

import Connect4.model.gridComponent.GridInterface
import Connect4.utils.UndoManager

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  val undoManager: UndoManager
  var players: List[Int]
  var winner: Int
  var grid: GridInterface


  def startGame(): Unit

  def move(column: Int): Unit

  def undo(): Unit

  def redo(): Unit

  def checkForWinner(): Boolean

  def nextPlayer(): Unit

  def setPlayer(player: Int): Unit

  def save(): Unit

  def load(): Unit
}
