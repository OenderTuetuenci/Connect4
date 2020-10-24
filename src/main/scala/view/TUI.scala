package view

import controller.{Controller, blockedColumnEvent, endGameEvent, startGameEvent, updateGridEvent}
import scala.swing.Reactor

class TUI(controller: Controller) extends Reactor {
  listenTo(controller)
  reactions += {
    case event:startGameEvent=> printTui(startGame)
                                printTui(showGrid)
                                printTui(askInput)
    case event:updateGridEvent=>printTui(showGrid)
                                printTui(askInput)
    case event:blockedColumnEvent=>printTui(blockedColumn)
    case event:endGameEvent=>printTui(endGame)
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
  def printTui(str: String):Unit={
    print(str)
  }
}
