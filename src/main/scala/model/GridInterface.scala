package model

import scala.swing.Publisher

trait GridInterface{
  var grid:Array[Array[Int]]
  val rows:Int
  val columns:Int

  def put(column:Int,player:Int):(Int,Int)
  def checkConnect4(stone:(Int,Int),player:Int):Boolean
  def resetValue(row:Int,column:Int):Unit
  def getRows:Int
  def getColumns:Int
}
