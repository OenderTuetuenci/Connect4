package view

abstract class TUI {

  //
  def processInput(input:String)

  def showBoard(): Unit ={
    println()
  }
}
