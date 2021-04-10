package Connect4.controller.utils

trait Command {
  def doStep():Unit
  def undoStep():Unit
  def redoStep():Unit
}
