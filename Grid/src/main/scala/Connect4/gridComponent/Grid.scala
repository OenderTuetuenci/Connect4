package Connect4.gridComponent

import play.api.libs.json.Json

import scala.annotation.tailrec

case class Grid(grid: Vector[Int] = Vector.fill(6 * 7)(0), rows: Int = 6, columns: Int = 7,
                limit: Vector[Int] = (35 to 41).toVector) extends GridInterface {

  def up(i: Int, m: Int): Int = i - m
  def down(i: Int, m: Int): Int = i + m

  def verticalMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 7)
  def horizontalMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 1)
  def diagonalLeftMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 6)
  def diagonalRightMove(direction: (Int, Int) => Int)(i: Int): Int = direction(i, 8)

  override def set(i: Int, value: Int): GridInterface = this.copy(grid = grid.updated(i, value))

  override def put(column: Int, player: Int): Option[(Int, GridInterface)] = {
    if (limit(column) > 0 && grid(column) > 0) {
      None
    } else {
      var i = column
      while (i < limit(column) && grid(verticalMove(down)(i)) == 0) {
        i = verticalMove(down)(i)
      }
      Some(i, this.copy(grid = grid.updated(i, player)))
    }
  }

  override def checkConnect4(player: Int): Boolean = {
    val horizontal = horizontalMove(down) _
    val vertical = verticalMove(down) _
    val diagonalLeft = diagonalLeftMove(down) _
    val diagonalRight = diagonalRightMove(down) _

    val horizontalCheck = checkHelperOuter((0 to 35 by 7).toVector, (6 to 41 by 7).toVector, horizontal, player, 0, 0, false)
    val verticalCheck = checkHelperOuter((0 to 6).toVector, (35 to 41).toVector, vertical, player, 0, 0, false)
    val diagonalRightCheck = checkHelperOuter(Vector(35, 28, 21, 14, 7, 0, 1, 2, 3, 4, 5, 6), Vector(35, 36, 37, 38, 39, 40, 41, 34, 27, 20, 13, 6), diagonalRight, player, 0, 0, false)
    val diagonalLeftCheck = checkHelperOuter(Vector(41, 34, 27, 20, 13, 6, 5, 4, 3, 2, 1, 0), Vector(41, 40, 39, 38, 37, 36, 35, 28, 21, 14, 7, 0), diagonalLeft, player, 0, 0, false)
    verticalCheck || horizontalCheck || diagonalRightCheck || diagonalLeftCheck
  }

  @tailrec
  private def checkHelperOuter(startpoints: Vector[Int], endpoints: Vector[Int], move: Int => Int, player: Int, index: Int, result: Int, end: Boolean): Boolean = {
    if (!end && index < startpoints.length) {
      val result = checkHelperInner(startpoints(index), endpoints(index), move, player, 0, false, startpoints(index))
      checkHelperOuter(startpoints, endpoints, move, player, index + 1, result, result >= 4)
    } else {
      end
    }
  }

  @tailrec
  private def checkHelperInner(index: Int, endpoint: Int, move: Int => Int, player: Int, counter: Int, first: Boolean, old: Int): Int = {
    if (index <= endpoint && counter < 4) {
      if (old == player && grid(index) == player) {
        checkHelperInner(move(index), endpoint, move, player, counter + 1, first, old = grid(index))
      } else if (grid(index) == player) {
        if (!first) {
          checkHelperInner(move(index), endpoint, move, player, counter + 1, first = true, old = grid(index))
        } else {
          checkHelperInner(move(index), endpoint, move, player, counter, first = false, old = grid(index))
        }
      } else {
        checkHelperInner(move(index), endpoint, move, player, counter = 0, first = false, old = grid(index))
      }
    } else
      counter
  }

  override def toString: String = {
    var temp = grid.mkString
    for (i <- 7 to temp.length by 8) {
      temp = temp.patch(i, "\n", 0)
    }
    temp
  }

  override def rebuild(json: String): GridInterface = {
    var grid:GridInterface = Grid()
    val tmp = Json.parse(json)
    for(index <- 0 to 41){
      val idx = (tmp \\ "index")(index).as[Int]
      val value = (tmp \\ "val")(index).as[Int]
      grid = grid.set(idx,value)
    }
    grid
  }
}
