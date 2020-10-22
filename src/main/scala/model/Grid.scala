package model

class Grid {
  val grid: Array[Array[Int]] = Array.ofDim[Int](6, 7)

  /*
    Puts Stone in Grid like in Real Connect 4
    0 is free space
    > 0 is taken space
    Returns (-1,-1) when column is already full
    else returns (row,column) where stone got put
   */
  def put(column: Int, player: Int): (Int, Int) = {
    if (grid(0)(column) > 0) {
      (-1, -1)
    } else {
      var row: Int = 0
      while (row <= 4 && grid(row + 1)(column) == 0) {
        row += 1
      }
      grid(row)(column) = player
      (row, column)
    }
  }

  override def toString: String = grid.map(_.mkString).mkString("\n")

}
