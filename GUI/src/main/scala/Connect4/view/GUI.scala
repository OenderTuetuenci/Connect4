package Connect4.view

import Connect4.controller.{ControllerInterface, blockedColumnEvent, endGameEvent, saveGameEvent, updateAllGridEvent, updateGridEvent}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import org.joda.time.DurationFieldType.millis
import play.api.libs.json.Json

import javax.swing.border.LineBorder
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.swing._
import scala.swing.event.ButtonClicked
import scala.sys.exit
import scala.util.{Failure, Success}

class GUI(controller: ControllerInterface) extends Frame {
  listenTo(controller)
  title = "Connect4"
  var cells: Vector[Label] = Vector()
  var buttons: Vector[Button] = Vector()
  contents = new GridBagPanel() {
    def constraints(x: Int, y: Int,
                    gridwidth: Int = 1, gridheight: Int = 1,
                    weightx: Double = 0.0, weighty: Double = 0.0,
                    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
    : Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridwidth
      c.gridheight = gridheight
      c.weightx = weightx
      c.weighty = weighty
      c.fill = fill
      c
    }
    add(new FlowPanel {
      for {i <- 0 to 6} {
        val button = new Button(i.toString)
        buttons = buttons :+ button
        contents += button
        listenTo(button)
      }
      reactions += {
        case ButtonClicked(b) => controller.move(b.text) match {
          case Success(_)=>
          case Failure(_)=>Dialog.showMessage(null,"Input is not valid")
        }
      }
    }, constraints(1, 1))

    add(new GridPanel(6, 7) {
      border = new LineBorder(java.awt.Color.BLACK, 2)
      for {index <- 0 to 41} {
        val label = new Label("IIII") {
          border = new LineBorder(java.awt.Color.BLACK, 1)
          foreground = java.awt.Color.WHITE
          opaque = false
        }
        cells = cells :+ label
        contents += label
      }
    }, constraints(1, 2, gridheight=15, weighty = 1.0, fill=GridBagPanel.Fill.Both))
  }

  def updateGrid(index: Int, player: Int): Unit = {
    if (player == 1)
      cells(index).foreground = java.awt.Color.YELLOW
    else if (player == 2)
      cells(index).foreground = java.awt.Color.RED
    else
      cells(index).foreground = java.awt.Color.WHITE
    repaint
  }

  def endGame(): Unit = {
    for (b <- buttons) {
      b.enabled = false
    }
    Dialog.showMessage(null, "Player " + controller.players.head + " won!!")
  }

  def blockedColumn(): Unit = {
    Dialog.showMessage(null, "Column is already Full!!")
  }

  def saveGame(): Unit = {
    Dialog.showMessage(null, "saved Game!!")
  }

  def updateAllGrid(): Unit = {
    val grid = controller.grid.grid
    for (index <- 0 to 41) {
      if (grid(index) == 0) {
        cells(index).foreground = java.awt.Color.WHITE
      } else if (grid(index) == 1) {
        cells(index).foreground = java.awt.Color.YELLOW
      } else {
        cells(index).foreground = java.awt.Color.RED
      }
    }
    Dialog.showMessage(null, "loaded Game. Player " + controller.players.head + " turn")
    repaint
  }

  override def closeOperation(): Unit = exit(0)

  reactions += {
    case e: updateGridEvent => updateGrid(e.stone, e.player)
    case e: endGameEvent => endGame()
    case e: blockedColumnEvent => blockedColumn()
    case e: saveGameEvent => saveGame()
    case e: updateAllGridEvent => updateAllGrid()
  }
  this.visible = true
  
}
