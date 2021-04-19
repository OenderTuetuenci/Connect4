package Connect4.controller.controllerComponent

import Connect4.controller.Commands.MoveCommand
import Connect4.controller.utils.UndoManager
import Connect4.controller.{ControllerInterface, saveGameEvent, startGameEvent, updateAllGridEvent}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.google.inject.Inject
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.language.postfixOps
import scala.util.{Failure, Try}

class Controller @Inject()() extends ControllerInterface with PlayJsonSupport {
  val undoManager = new UndoManager
  var players: List[Int] = 1 :: 2 :: Nil
  var winner: Int = 0

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  def startGame(): Unit = publish(new startGameEvent)

  def save(): Unit = {
    val status = {
      val response = Http().singleRequest(Post("http://localhost:8080/model/fileIo/save?player=" + players.head))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
      Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
    }
    publish(new saveGameEvent)
  }

  def load(): Unit = {
    val player = {
      val response = Http().singleRequest(Post("http://localhost:8080/model/fileIo/load"))
      val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Int])
      Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
    }
    setPlayer(player)
    publish(new updateAllGridEvent)
  }

  def setPlayer(player: Int): Unit = {
    if (player == 1)
      players = 1 :: 2 :: Nil
    else
      players = 2 :: 1 :: Nil
  }

  def move(column: String): Try[Unit] = {
    if (column.toIntOption.isDefined) {
      Try(undoManager.doStep(new MoveCommand(column.toInt, players.head, this)))
    } else {
      Failure(new Exception("Input is not a valid Input (s, l, u, r, q, 0 - 6)\n"))
    }
  }

  def undo(): Unit = undoManager.undoStep()

  def redo(): Unit = undoManager.redoStep()

  def checkForWinner(): Boolean = {
    val response = Http().singleRequest(Post("http://localhost:8080/model/grid/checkConnect?player=" + players.head))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[Boolean])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def nextPlayer(): Unit = players = players.tail ::: List(players.head)
}