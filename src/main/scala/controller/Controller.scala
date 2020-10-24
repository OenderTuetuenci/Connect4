package controller

import java.util.Scanner

import model.{Grid, Player}
import utils.{Observable, Observer}

class Controller(grid:Grid) extends Observable{
  var players: List[Int] = 1::2::Nil
  var winner:Int = 0

  def getGridString :String = grid.toString
  def startGame() : Unit = notifyObservers("startGame")

  def move(column: Int): Unit = {
    val loc = grid.put(column, players.head)
    var end: Boolean = false
    if(loc.equals((-1,-1)))
      notifyObservers("blockedColumn")
    else {
      notifyObservers("updateGrid")
      end = checkForWinner(loc)
      if(!end)
        nextPlayer()
      else
        notifyObservers("endGame")
    }
  }

  def checkForWinner(stone:(Int,Int)): Boolean ={
    grid.checkConnect4(stone,players.head)
  }

  def nextPlayer():Unit = players = players.tail ::: List(players.head)
}
