package Connect4.gridComponent.DBComponent.MySqlImpl

import Connect4.gridComponent.DBComponent.DAO
import Connect4.gridComponent.utils.gridTable.GridTable
import Connect4.gridComponent.{GridInterface, GridModule}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.google.inject.{Guice, Injector}
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MySql extends DAO{
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext
  val injector: Injector = Guice.createInjector(new GridModule)
  val gridTable = TableQuery[GridTable]
  val db = Database.forURL(
    url = "jdbc:mysql://0.0.0.0:3306/connect4?serverTimezone=UTC", //change database to localhost if u are not running with with docker compos
    driver = "com.mysql.cj.jdbc.Driver",
    user = "connect4",
    password = "bestpasswordever"
  )

  override def create(): Unit = {
    val setup = DBIO.seq(gridTable.schema.createIfNotExists)
    Await.result(db.run(setup),Duration(10,TimeUnit.SECONDS))
    for(i <- 0 to 41){
      val updateQuery = {
        gridTable.filter(_.index===i).exists.result.flatMap(exists=>
          if(!exists){
            gridTable += (i,0)
          }else{
            DBIO.successful(None)
          }
        ).transactionally
      }
      Await.result(db.run(updateQuery),Duration.Inf)
    }
  }

  override def read(): GridInterface = {
    var grid: GridInterface = injector.getInstance(classOf[GridInterface])
    val selectQuery = for (i<- gridTable) yield (i.index,i.value)
    val gridTuples = Await.result(db.run(selectQuery.result),Duration.Inf)
    for(i<-gridTuples){
      grid = grid.set(i._1,i._2)
    }
    grid
  }

  override def update(grid: GridInterface): Unit = {
    for(index<-grid.grid.indices){
      val value = grid.grid(index)
      val updateQuery = gridTable.filter(_.index === index).update((index,value))
      Await.result(db.run(updateQuery),Duration.Inf)
    }
  }

  override def delete(): Unit = Await.result(db.run(gridTable.delete),Duration.Inf)
}
