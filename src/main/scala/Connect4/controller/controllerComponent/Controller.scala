package Connect4.controller.controllerComponent

import com.google.inject.Inject
import Connect4.controller.Commands.MoveCommand
import Connect4.controller.{saveGameEvent, startGameEvent, updateAllGridEvent, updateGridEvent}
import Connect4.gridComponent.GridInterface
import Connect4.utils.{HTTPRequestHandler, UndoManager}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.{Get, Post}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import fileIoComponent.FileIOInterface

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

class Controller @Inject() () extends ControllerInterface with PlayJsonSupport{
  var players: List[Int] = 1::2::Nil
  var winner:Int = 0
  val undoManager = new UndoManager
  val requestHandler = new HTTPRequestHandler()

  def startGame() : Unit = publish(new startGameEvent)
  def save():Unit = {
    fileIo.save(grid,players.head)
    publish(new saveGameEvent)
  }
  def load():Unit ={
    val stats = fileIo.load
    grid=stats._1
    setPlayer(stats._2)
    publish(new updateAllGridEvent)
  }
  def move(column: String): Try[Unit] = {
    if (column.toIntOption.isDefined){
      Try(undoManager.doStep(new MoveCommand(column.toInt,players.head,this)))
    } else {
      Failure(new Exception("Input is not a valid Input (s, l, u, r, q, 0 - 6)\n"))
    }
  }

  def undo():Unit= undoManager.undoStep()
  def redo():Unit= undoManager.redoStep()

  def checkForWinner(): Boolean = requestHandler.checkWinner(players.head)
  def nextPlayer():Unit = players = players.tail ::: List(players.head)
  def setPlayer(player:Int):Unit={
    if(player == 1)
      players = 1::2::Nil
    else
      players = 2::1::Nil
  }
}