package Connect4.controller.DBComponent.MySqlImp

import Connect4.controller.DBComponent.DAO
import Connect4.utils.playerTable.PlayerTable
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MySql extends DAO{
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext
  val playerTable = TableQuery[PlayerTable]
  val db = Database.forURL(
    url = "jdbc:mysql://localhost:3306/connect4?serverTimezone=UTC",
    driver = "com.mysql.cj.jdbc.Driver",
    user = "connect4",
    password = "bestpasswordever"
  )

  override def create(): Unit = {
    val setup = DBIO.seq(playerTable.schema.createIfNotExists)
    Await.result(db.run(setup),Duration(10,TimeUnit.SECONDS))
    val insertQuery = {
      playerTable.filter(_.id===0).exists.result.flatMap(exists=>
        if(!exists){
          playerTable += (0,0)
        }else{
          DBIO.successful(None)
        }
      ).transactionally
    }
    Await.result(db.run(insertQuery),Duration(10,TimeUnit.SECONDS))
  }
  override def read(): Int = {
    val readQuery = playerTable.take(1).result
    val player = Await.result(db.run(readQuery),Duration.Inf)
    player.head._2
  }

  override def update(player:Int): Unit = {
    val updateQuery = playerTable.filter(_.id === 0).update((0,player))
    Await.result(db.run(updateQuery),Duration(10,TimeUnit.SECONDS))
  }

  override def delete(): Unit = Await.result(db.run(playerTable.delete),Duration.Inf)
}
