package Connect4.model.gridComponent

case class Grid(grid: Vector[Int] = Vector.fill(6 * 7)(0), rows: Int = 6, columns: Int = 7,
                limit: Vector[Int] = (35 to 41).toVector) extends GridInterface {

  def up(i: Int, m: Int): Int = i - m

  override def set(i: Int, value: Int): GridInterface = this.copy(grid = grid.updated(i, value))

  override def put(column: Int, player: Int): (Option[Int], GridInterface) = {
    if (grid(column) > 0) {
      (None, this)
    } else {
      var i = column
      while (i < limit(column) && grid(verticalMove(down)(i)) == 0) {
        i = verticalMove(down)(i)
      }
      (Some(i), this.copy(grid = grid.updated(i, player)))
    }
  }

  def down(i: Int, m: Int): Int = i + m

  def verticalMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 7)

  override def checkConnect4(player: Int): Boolean = {
    val horizontal = horizontalMove(down) _
    val vertical = verticalMove(down) _
    val diagonalLeft = diagonalLeftMove(down) _
    val diagonalRight = diagonalRightMove(down) _

    val horizontalCheck = check((0 to 35 by 7).toVector, (6 to 41 by 7).toVector, horizontal, player)
    val verticalCheck = check((0 to 6).toVector, (35 to 41).toVector, vertical, player)
    val diagonalRightCheck = check(Vector(35, 28, 21, 14, 7, 0, 1, 2, 3, 4, 5, 6), Vector(35, 36, 37, 38, 39, 40, 41, 34, 27, 20, 13, 6), diagonalRight, player)
    val diagonalLeftCheck = check(Vector(41, 34, 27, 20, 13, 6, 5, 4, 3, 2, 1, 0), Vector(41, 40, 39, 38, 37, 36, 35, 28, 21, 14, 7, 0), diagonalLeft, player)

    diagonalLeftCheck || diagonalRightCheck || verticalCheck || horizontalCheck
  }

  def horizontalMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 1)

  def diagonalLeftMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 6)

  def diagonalRightMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 8)

  private def check(startpoints: Vector[Int], endpoints: Vector[Int], move: Int => Int, player: Int): Boolean = {
    var counter = 0
    var fourInRow = false
    for (i <- startpoints.indices) {
      var idx = startpoints(i)
      var old = grid(idx)
      var first = false
      while (idx <= endpoints(i) && counter < 4) {
        if (grid(idx) == player && !first) {
          counter += 1
          first = true
        } else if (old == player && grid(idx) == player) {
          counter += 1
        } else if (grid(idx) != player && counter < 4) {
          counter = 0
          first = false
        }
        old = grid(idx)
        idx = move(idx)
      }
      if (counter >= 4)
        fourInRow = true
    }
    fourInRow
  }

  override def toString: String = {
    var temp = grid.mkString
    for (i <- 7 to temp.length by 8) {
      temp = temp.patch(i, "\n", 0)
    }
    temp
  }
}
