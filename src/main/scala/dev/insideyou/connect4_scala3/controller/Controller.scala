package dev.insideyou.connect4_scala3.controller

import scala.swing.Publisher
import scala.util.Try
import dev.insideyou.connect4_scala3.utils.UndoManager
import dev.insideyou.connect4_scala3.model.Grid
import scala.util.Failure

class Controller(
    var grid: Grid = Grid(),
    undoManager: UndoManager = UndoManager(),
    var players: List[Int] = 1 :: 2 :: Nil,
    var winner: Int = 0,
  ) extends Publisher:
  def startGame(): Unit = publish(startGameEvent())
  def move(column: String): Try[Unit] =
    if (column.toIntOption.isDefined)
      Try(undoManager.doStep(MoveCommand(column.toInt, players.head, this)))
    else
      Failure(new Exception("Input is not a valid Input (u, r, q, n, 0 - 6)\n"))
  def undo(): Unit = undoManager.undoStep()
  def redo(): Unit = undoManager.redoStep()
  def newGame(): Unit = 
    grid = Grid()
    publish(updateAllGridEvent())
  def checkForWinner(): Boolean = grid.checkConnect4(players.head)
  def nextPlayer(): Unit = players = players.tail ::: List(players.head)
  def setPlayer(player: Int): Unit =
    if (player == 1)
      players = 1 :: 2 :: Nil
    else
      players = 2 :: 1 :: Nil
