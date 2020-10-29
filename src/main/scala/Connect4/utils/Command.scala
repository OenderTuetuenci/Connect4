package Connect4.utils

trait Command {
  def doStep():Unit
  def undoStep():Unit
  def redoStep():Unit
}
