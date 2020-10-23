import model.{Grid, Player}

import scala.io.StdIn
import java.util.Scanner

import controller.Controller

import view.TUI

object Connect4 {
  def main(args: Array[String]): Unit = {
    val controller = new Controller(new Grid)
    val tui = new TUI(controller)
    //controller.notifyObservers
    
    tui.startGame()

  }
}
