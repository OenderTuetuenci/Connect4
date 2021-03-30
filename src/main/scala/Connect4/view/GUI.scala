package Connect4.view

import Connect4.controller.controllerComponent.{Controller, ControllerInterface}
import Connect4.controller.{blockedColumnEvent, endGameEvent, saveGameEvent, updateAllGridEvent, updateGridEvent}
import javax.swing.border.LineBorder
import Connect4.model.gridComponent.GridInterface

import scala.swing._
import scala.swing.event.ButtonClicked
import scala.sys.exit

class GUI(controller: ControllerInterface) extends Frame {
  listenTo(controller)
  title = "Connect4"
  var cells: Vector[Label] = Vector()
  var buttons: Vector[Button] = Vector()
  contents = new GridPanel(2, 0) {
    contents += buttonPanel
    contents += gridPanel
  }

  def buttonPanel: FlowPanel = new FlowPanel {
    for {i <- 0 to 6} {
      val button = new Button(i.toString)
      buttons = buttons :+ button
      contents += button
      listenTo(button)
    }
    reactions += {
      case ButtonClicked(b) => controller.move(b.text.toInt)
    }
  }

  def gridPanel: GridPanel = new GridPanel(6, 7) {
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
