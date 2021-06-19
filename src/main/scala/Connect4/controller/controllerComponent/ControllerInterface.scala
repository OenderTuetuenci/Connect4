package Connect4.controller.controllerComponent

import Connect4.utils.{HTTPRequestHandler, UndoManager}

import scala.swing.Publisher
import scala.util.Try
/** Trait for Controller */
trait ControllerInterface extends Publisher {
  /** Handles undo and redo */
  val undoManager: UndoManager
  /** List of Players */
  var players: List[Int]
  /** Current Winner */
  var winner: Int
  /** Handles all Rest Calls */
  val requestHandler:HTTPRequestHandler
  /** starts the Game */
  def startGame(): Unit

  /**
   * Put Stone in desired Column
   * @param column desired column
   * @return a Try Which checks if Move was valid or not
   */
  def move(column: String): Try[Unit]
  /** Undo current turn */
  def undo(): Unit
  /** Redo last turn */
  def redo(): Unit

  /**
   * Checks if someone has won
   * @return True if someone has won else false
   */
  def checkForWinner(): Boolean
  /** Swap to next Player */
  def nextPlayer(): Unit

  /**
   * Set who´s turn it is
   * @param player
   */
  def setPlayer(player: Int): Unit
  /** Save Current Gamestate */
  def save(): Unit
  /** Save Current Gamestate */
  def load(): Unit
}
