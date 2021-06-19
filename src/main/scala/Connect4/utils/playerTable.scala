package Connect4.utils
import slick.lifted.ProvenShape
import slick.jdbc.MySQLProfile.api._

object playerTable {
  class PlayerTable(tag:Tag) extends Table[(Int,Int)](tag,"PLAYER"){
    def * : ProvenShape[(Int,Int)] = (id,player)
    def id : Rep[Int] = column[Int]("Game_ID",O.PrimaryKey)
    def player : Rep[Int] = column[Int]("Player",O.SqlType("INT"))
  }
}
