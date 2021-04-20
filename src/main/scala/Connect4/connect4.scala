package Connect4

import Connect4.gridComponent.Grid
import com.google.inject.{Guice, Injector}
import controller.controllerComponent.ControllerInterface
import view.GUI
import view.TUI

object connect4 {
  val injector: Injector = Guice.createInjector(new Connect4Module)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new TUI(controller)
  val gui = new GUI(controller)
  controller.startGame()

  def main(args: Array[String]): Unit = {
    //Loop for Tui
    var input: String = ""
    do {
      input = scala.io.StdIn.readLine()
      tui.handleInput(input)
    } while (!input.equals("q") || controller.winner == 0)
    gui.closeOperation()
  }
}
