package Connect4.gridComponent.utils

import slick.lifted.ProvenShape
import slick.jdbc.MySQLProfile.api._

object gridTable {
  class GridTable(tag:Tag) extends Table[(Int,Int)](tag,"GRID"){
    def * : ProvenShape[(Int,Int)] = (index,value)
    def index : Rep[Int] = column[Int]("INDEX",O.SqlType("INT"))
    def value : Rep[Int] = column[Int]("Value",O.SqlType("INT"))
  }
}
