package view

import java.lang.ModuleLayer.Controller
import java.util.Scanner

import model.Grid
import utils.Observer

class TUI(){

  //controller.add(this)

  def startGame(): Unit ={
    println("Willkommen zu Vier gewinnt!")

    val grid = new Grid
    val players = 1::2::Nil
    val scanner = new Scanner(System.in)

    move(grid, players, scanner)


  }

  def move(grid: Grid, players:List[Int], scanner:Scanner): Unit ={
    println(grid.toString)
    println("player " + players.head + " input column (between 0 and 6)")
    if (grid.checkConnect4(grid.put(scanner.nextInt(), players.head),players.head)) {
      println(grid.toString)
      println("Player " + players.head + " hat gewonnen!")
      return
    }
    move(grid, players.tail:::players.head::Nil, scanner)
  }
}
