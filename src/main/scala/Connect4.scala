import scala.util.control
import model.Grid

import scala.io.StdIn
import java.util.Scanner
import scala.util.control.Breaks.break

object Connect4 {
  def main(args: Array[String]): Unit = {
    val test = new Grid


    val scanner = new Scanner(System.in)

    while(true){
      println(test.toString)
      println("player 1 input column (between 0 and 6)")
      var input = scanner.next()
      if (input == "q") break // well it doesn't work....
      test.put(input.toInt, 1)
      if (test.checkConnect4((input.toInt,0),1)) break() // doesn't work either

      println(test.toString)
      println("player 2 input column (between 1 and 7)")
      input = scanner.next()
      if (input == "q") break
      test.put(input.toInt, 2)

      if (test.checkConnect4((input.toInt,0),2)) break() // doesn't work either
    }

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
  }
}
