package Connect4.controller.controllerComponent

import Connect4.utils.{HTTPRequestHandler, UndoManager}

import scala.swing.Publisher
import scala.util.Try

trait ControllerInterface extends Publisher {
  val undoManager: UndoManager
  var players: List[Int]
  var winner: Int
  val requestHandler:HTTPRequestHandler

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
