package Connect4.view

import Connect4.controller.controllerComponent.{Controller, ControllerInterface}
import Connect4.controller.{blockedColumnEvent, endGameEvent, saveGameEvent, startGameEvent, updateAllGridEvent, updateGridEvent}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.swing.{Reactor, implicitConversions}
import scala.util.{Failure, Success, Try}

class TUI(controller: ControllerInterface) extends Reactor with PlayJsonSupport{
  listenTo(controller)
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext
  reactions += {
    case event:startGameEvent=> printTui(startGame)
                                printTui(showGrid)
                                printTui(askInput)
    case event:updateGridEvent=>printTui(showGrid)
                                printTui(askInput)
    case event:blockedColumnEvent=>printTui(blockedColumn)
    case event:endGameEvent=>printTui(endGame)
    case event:updateAllGridEvent=>printTui(showGrid)
                                  printTui(askInput)
    case event:saveGameEvent=>printTui(saveGame)
  }

  def showGrid: String = controller.requestHandler.showGridTui() + "\n"
  def startGame:String = "Welcome to Connect4!\n"
  def blockedColumn:String = "This Column is already full!!\n"
  def endGame:String = "Player "+controller.players.head+ " won!!\n"
  def askInput:String = "Player "+controller.players.head+" input column (between 0 and 6)\n"
  def quit:String = "Quitting Game\n"
  def saveGame:String="saved Game\n"
  def handleInput(input:String): Unit ={
    input match {
      case "s"=>controller.save()
      case "l"=>controller.load()
      case "u"=>controller.undo()
      case "r"=>controller.redo()
      case "q"=>controller.winner = -1
                print(quit)
      case _ => for {
        i <- input.trim
      }yield controller.move(i.toString) match {
        case Success(_)=>
        case Failure(exception)=>printTui(exception.getMessage)
      }
    }
  }
  def printTui(str: String):Unit={
    print(str)
  }
}
