package Connect4.controller.controllerComponent

import Connect4.Connect4Module
import Connect4.controller.Commands.MoveCommand
import Connect4.controller.DBComponent.DAO
import Connect4.controller.{saveGameEvent, startGameEvent, updateAllGridEvent}
import Connect4.utils.{HTTPRequestHandler, UndoManager}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.google.inject.{Guice, Inject, Injector}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.util.{Failure, Success, Try}

class Controller @Inject() () extends ControllerInterface with PlayJsonSupport{
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext
  val injector: Injector = Guice.createInjector(new Connect4Module)
  val dataBase: DAO = injector.getInstance(classOf[DAO])
  var players: List[Int] = 1::2::Nil
  var winner:Int = 0
  val undoManager = new UndoManager
  val requestHandler = new HTTPRequestHandler()

  dataBase.create() onComplete{
    case Success(_) => println("created DB")
    case Failure(exception) => println(exception.getMessage)
  }

  def startGame() : Unit = publish(new startGameEvent)
  def save():Unit = {
    val status = requestHandler.saveGame()
    dataBase.update(players.head) onComplete {
      case Success(_) => publish(new saveGameEvent)
      case Failure(exception) => println(exception.getMessage)
    }
  }
  def load():Unit ={
    val player = dataBase.read()
    val stats = requestHandler.loadGame()
    setPlayer(player)
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