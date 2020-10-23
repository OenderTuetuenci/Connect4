import model.{Grid, Player}

import scala.io.StdIn
import java.util.Scanner

import scala.util.control.Breaks.break
import view.TUI

object Connect4 {
  def main(args: Array[String]): Unit = {
    val test = new Grid
    val scanner = new Scanner(System.in)

    // note: statt schleife eine endrekursion aufrufen. That would be proper functional programming!
    var input = "0"

   def move(player:Int): Unit ={
     if (test.checkConnect4(test.put(input.toInt, player),player)) {
       println(test.toString)
       println("Player " + player + " hat gewonnen!")
       input = "q"
     }
   }

    var players = 1::2::Nil

    def processInput(){}

    do {
      println(test.toString)
      println("player " + players.head + " input column (between 0 and 6)")
      input = scanner.next()
      if (input != "q") move(players.head)
      players = players.tail:::players.head::Nil

    } while(input != "q")

    /*
    test.put(1,2)
    test.put(2,1)
    test.put(2,2)
    test.put(3,1)
    test.put(3,1)
    test.put(3,2)
    test.put(4,1)
    test.put(4,1)
    test.put(4,1)
    test.put(4,2)
    test.put(2,1)
    test.put(1,1)
    test.put(1,1)
    test.put(1,1)
    //test.put(0,1)
    //test.put(2,1)
    //test.put(3,1)
    //test.put(1,2)
    //test.put(1,2)
    //test.put(1,2)
    println(test.toString)
    println(test.checkConnect4((0,6),2))
    */
  }
}
