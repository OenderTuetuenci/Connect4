package dev.insideyou.connect4_scala3.view

import scala.swing.Reactor
import dev.insideyou.connect4_scala3.controller.*
import scala.util.Success
import scala.util.Failure

class TUI(controller:Controller) extends Reactor:
    listenTo(controller)
    reactions += {
    case event:startGameEvent=> printTui(startGame)
                                printTui(showGrid)
                                printTui(askInput)
    case event:updateGridEvent=>printTui(showGrid)
                                printTui(askInput)
    case event:blockedColumnEvent=>printTui(blockedColumn)
    case event:endGameEvent=>printTui(endGame)
    case event:updateAllGridEvent=>printTui(showGrid)
                                  printTui(askInput)
    case event:saveGameEvent=>printTui(saveGame)
    }
    def showGrid: String = controller.grid.toString+"\n"
    def startGame:String = "Welcome to Connect4!\n"
    def blockedColumn:String = "This Column is already full!!\n"
    def endGame:String = "Player "+controller.players.head+ " won!!\n"
    def askInput:String = "Player "+controller.players.head+" input column (between 0 and 6)\n"
    def quit:String = "Quitting Game\n"
    def saveGame:String="saved Game\n"
    def handleInput(input:String): Unit =
        input match
            case "u" =>  controller.undo()
            case "r" =>  controller.redo()
            case "n" =>  controller.newGame()
            case "q" =>  controller.winner = -1
                        print(quit)
            case _   => 
                controller.move(input) match
                    case Success(_) =>
                    case Failure(exception) => printTui(exception.getMessage.nn)
    def printTui(str: String):Unit = print(str)
    

