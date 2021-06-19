package Connect4.utils
/** Trait for Command */
trait Command {
  def doStep():Unit
  def undoStep():Unit
  def redoStep():Unit
}
