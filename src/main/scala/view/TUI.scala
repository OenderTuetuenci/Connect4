package view

import controller.Controller
import java.util.Scanner

import model.Grid
import utils.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)
  override def update(e:String): Unit ={
    e match {
      case "updateGrid" =>print(showGrid)
                          print(askInput)
      case "startGame"=>print(startGame)
                        print(showGrid)
                        print(askInput)
      case "blockedColumn"=>print(blockedColumn)
      case "endGame"=>print(endGame)
      case "askInput"=>print(askInput)
      case _ =>
    }
  }

  def showGrid: String = controller.getGridString+"\n"
  def startGame:String = "Welcome to Connect4!\n"
  def blockedColumn:String = "This Column is already full!!\n"
  def endGame:String = "Player "+controller.players.head+ " won!!\n"
  def askInput:String = "Player "+controller.players.head+" input column (between 0 and 6)\n"
  def quit:String = "Quitting Game"
  def handleInput(input:String): Unit ={
    input match {
      case "u"=>controller.undo()
      case "r"=>controller.redo()
      case "q"=>controller.winner = -1
                print(quit)
      case ""=>
      case _ =>controller.move(input.toInt)
    }
  }
}
