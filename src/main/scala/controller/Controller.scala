package controller

import java.util.Scanner

import model.{Grid, Player}
import org.graalvm.compiler.graph.Node.Input
import utils.Observable

class Controller(grid:Grid) extends Observable{
  var players = 1::2::Nil

  def showGrid(): String = grid.toString

  def getPlayer(): Int = {
    players.head
  }

  def move(column: Int): (Int, Int) ={
    grid.put(column, players.head)
    //move(grid, players.tail:::players.head::Nil, input)

  }

  def checkForWinner(input:Int): Boolean ={
    if (grid.checkConnect4(move(input),players.head)) return true
    nextPlayer()
    false
  }

  def nextPlayer()= players = players.tail ::: List(players.head)
}
