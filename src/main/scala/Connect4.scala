import model.{Grid, Player}

import scala.io.StdIn
import java.util.Scanner

import controller.Controller

import scala.util.control.Breaks.break
import view.TUI

object Connect4 {
  def main(args: Array[String]): Unit = {
    val controller = new Controller
    val tui = new TUI()

    // note: statt schleife eine endrekursion aufrufen. That would be proper functional programming!

    def processInput(input: String): Unit ={

    }

    tui.startGame()

  }
}
