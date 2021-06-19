package Connect4.controller.DBComponent
/** Trait for Database Access Object */
trait DAO {
  /** Create Database if not already created */
  def create():Unit
  /**
   * Load who´s turn it is
   * @return Current Players turn
   */
  def read():Int
  /**
   * Save who´s turn it is
   * @param player
   */
  def update(player:Int):Unit
  /** Delete Database */
  def delete():Unit
}
