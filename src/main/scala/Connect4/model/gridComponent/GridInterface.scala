package Connect4.model.gridComponent

trait GridInterface {
  var grid: Vector[Int]
  val rows: Int
  val columns: Int

  def put(column: Int, player: Int): Option[Int]

  def checkConnect4(player: Int): Boolean

  def set(i: Int, value: Int): Unit

}
