package view

import controller.Controller
import java.util.Scanner

import model.Grid
import utils.Observer

class TUI(controller: Controller) extends Observer {

  //controller.add(this)
  def startGame(): Unit ={
    val scanner = new Scanner(System.in)
    println("Willkommen zu Vier gewinnt!")
    handleInput(scanner)
    println("Game ended")
  }

  def handleInput(scanner:Scanner): Unit ={
    printGrid()
    println("Player " + controller.getPlayer() + " input column (between 0 and 6)")

    if(controller.checkForWinner(scanner.nextInt())){
      println("Player " + controller.getPlayer() + " has won!")
      return
    }
    handleInput(scanner)
  }

  def printGrid() = println(controller.showGrid())

  override def update: Unit = println(controller.toString)
}
