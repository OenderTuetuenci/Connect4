package Connect4.model.gridComponent

class Grid extends GridInterface {
  var grid: Array[Array[Int]] = Array.ofDim[Int](6, 7)
  val rows:Int = 6
  val columns:Int = 7
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

  /*
  Checks if 4 stones connect
  if 4 connect returns true else false
  Double check what stone your are inserting!
   */
  def checkConnect4(stone: (Int, Int), player: Int): Boolean = {
    if (checkHorizontal(stone, player) || checkVertical(stone, player) || checkDiagonal(stone, player)) {
      true
    } else {
      false
    }
  }

  /*
  Private Helper funtion for checking if 4 Connect Horizontal
  returns true when 4 or more Stones connect else false
   */
  private def checkHorizontal(stone: (Int, Int), player: Int): Boolean = {
    val row: Int = stone._1
    var count:Int = 0
    var old:Int = 0
    for (i <- 0 to 6) {
      if (grid(row)(i) == player && old == player)
        count += 1
      else if(grid(row)(i) != player && count < 3)
        count = 0
      old = grid(row)(i)
    }
    if (count >= 3)
      true
    else
      false
  }

  /*
  Private Helper funtion for checking if 4 Connect Vertical
  returns true when 4 or more Stones connect else false
 */
  private def checkVertical(stone: (Int, Int), player: Int): Boolean = {
    val column: Int = stone._2
    var count:Int = 0
    var old:Int = 0
    for (i <- 0 to 5) {
      if (grid(i)(column) == player && old == player)
        count += 1
      else if(grid(i)(column) != player && count < 3)
        count = 0
      old = grid(i)(column)
    }
    if (count >= 3)
      true
    else
      false
  }

  /*
  Private Helper funtion for checking if 4 Connect Diagonal
  returns true when 4 or more Stones connect else false
  */
  private def checkDiagonal(stone: (Int, Int), player: Int): Boolean = {
    var countlr: Int = 0 // Counter for diagonal from left to right
    var countrl: Int = 0 // Counter for diagonal from right to left

    //find start points
    val splr: (Int, Int) = findStartPoint(stone, "lr")
    val sprl: (Int, Int) = findStartPoint(stone, "rl")

    //check diagonal lr
    var r: Int = splr._1
    var c: Int = splr._2
    var o: Int = 0
    while (r < 6 && c > 0) {
      if (grid(r)(c) == player && o == player) {
        countlr += 1
      }else if(grid (r)(c) != player && countlr < 3)
        countlr = 0
      o = grid(r)(c)
      r += 1
      c -= 1
    }
    //check diagonal rl
    r = sprl._1
    c = sprl._2
    o = 0
    while (r < 6 && c < 7) {
      if (grid(r)(c) == player && o == player) {
        countrl += 1
      }else if(grid(r)(c) != player && countrl < 3)
        countrl = 0
      o = grid(r)(c)
      r += 1
      c += 1
    }
    if (countlr >= 3 || countrl >= 3)
      true
    else
      false
  }

  /*
  Helper Function to find startpoints for diagonal check
   */
  private def findStartPoint(stone: (Int, Int), direction: String): (Int, Int) = {
    var r: Int = stone._1 //5
    var c: Int = stone._2 //4
    if (direction.equals("rl")) {
      while (r > 0 && c > 0) {
        r -= 1
        c -= 1
      }
    } else {
      while (r > 0 && c < 6) {
        r -= 1
        c += 1
      }
    }
    (r, c)
  }

  def resetValue(row:Int,column:Int):Unit = grid(row)(column) = 0
  override def toString: String = grid.map(_.mkString).mkString("\n")
  def getRows :Int = rows
  def getColumns:Int = columns

  override def set(row: Int, col: Int, value: Int): Unit = {
    grid(row)(col) = value
  }
}
