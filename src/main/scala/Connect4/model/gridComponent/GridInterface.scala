package Connect4.model.gridComponent

trait GridInterface {
  def grid: Vector[Int]
  def rows: Int
  def columns: Int

  def put(column: Int, player: Int): (Option[(Int,GridInterface)])

  def checkConnect4(player: Int): Boolean

  def set(i: Int, value: Int): GridInterface

}
