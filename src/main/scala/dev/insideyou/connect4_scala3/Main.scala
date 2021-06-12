package dev.insideyou
package connect4_scala3

import model.Grid
import controller.Controller
import view.TUI

@main def connect4 =
  val controller = Controller()
  val tui = TUI(controller)
  controller.startGame()

  var input: String = ""
  while (!input.equals("q") && controller.winner == 0)
    input = scala.io.StdIn.readLine()
    tui.handleInput(input)
