import model.{Grid, Player}

import scala.io.StdIn.readLine

import controller.Controller

import view.TUI

object Connect4 {
  def main(args: Array[String]): Unit = {
    val controller = new Controller(new Grid)
    val tui = new TUI(controller)
    controller.startGame()

    //Loop for Tui
    var input:String = ""
    do{
      input = readLine()
      tui.handleInput(input)
    }while(!input.equals("q") || controller.winner == 0)
  }
}
