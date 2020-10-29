package Connect4

import com.google.inject.{Guice, Injector}
import controller.controllerComponent.ControllerInterface
import view.GUI
import view.TUI

object Connect4 {
  val injector: Injector = Guice.createInjector(new Connect4Module)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new TUI(controller)
  val gui = new GUI(controller)
  controller.startGame()

  def main(args: Array[String]): Unit = {
    //Loop for Tui
    var input: String = ""
    do {
      input = readLine()
      tui.handleInput(input)
    } while (!input.equals("q") || controller.winner == 0)
    gui.closeOperation()
  }
}
