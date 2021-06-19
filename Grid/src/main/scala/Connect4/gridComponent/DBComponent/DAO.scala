package Connect4.gridComponent.DBComponent

import Connect4.gridComponent.GridInterface

/**
 * Trait for Data Access Object
 */
trait DAO {
  /**
   * Creates Database if not already created
   */
  def create():Unit

  /**
   * Loads Gamestate from Database
   * @return a new GridInterface with loaded Gamestate
   */
  def read():GridInterface

  /**
   * Saves Gamestate into Database
   * @param grid Current Gamestate
   */
  def update(grid:GridInterface):Unit

  /**
   * Delete Database
   */
  def delete():Unit
}
