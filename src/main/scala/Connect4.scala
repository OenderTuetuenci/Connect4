import model.Grid

import scala.io.StdIn.readLine
import controller.Controller
import view.{GUI, TUI}

object Connect4 {
  val controller = new Controller(new Grid)
  val tui = new TUI(controller)
  val gui = new GUI(controller)
  controller.startGame()
  def main(args: Array[String]): Unit = {
    //Loop for Tui
    var input:String = ""
    do{
      input = readLine()
      tui.handleInput(input)
    }while(!input.equals("q") || controller.winner == 0)
    gui.closeOperation()
  }
}
