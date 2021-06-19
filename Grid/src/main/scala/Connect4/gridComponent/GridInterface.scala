package Connect4.gridComponent

/** Trait for Grid */
trait GridInterface {
  /** Datastructure for the Board */
  def grid: Vector[Int]
  /** Count of rows */
  def rows: Int
  /** Count of columns */
  def columns: Int

  /**
   * Put a Stone in the Board
   * @param column Desired column
   * @param player current Player
   * @return Tuple with location of Stone and a Instance of GridInterface with desired change
   */
  def put(column: Int, player: Int): (Option[(Int, GridInterface)])
  /**
   * Checks if a Player has 4 in a row
   * @param player Current Player
   * @return True if Player has 4 in row else False
   */
  def checkConnect4(player: Int): Boolean
  /**
   * Set index to value
   * @param i index
   * @param value value
   * @return a new Instance of GridInterface with desired change
   */
  def set(i: Int, value: Int): GridInterface
  /**
   * Rebuild Gamestate from Json
   * @param json
   * @return a new Instance of GridInterface with desired change
   */
  def rebuild(json:String):GridInterface
}
