package controller

import model.GridInterface
import utils.UndoManager

import scala.swing.Publisher

trait ControllerInterface extends Publisher{
  var players:List[Int]
  var winner:Int
  val undoManager:UndoManager
  var grid:GridInterface

  def getGridString:String
  def startGame():Unit
  def move(column:Int):Unit
  def undo():Unit
  def redo():Unit
  def checkForWinner(stone:(Int,Int)):Boolean
  def nextPlayer():Unit
  def resetPlayer(player:Int):Unit
}
