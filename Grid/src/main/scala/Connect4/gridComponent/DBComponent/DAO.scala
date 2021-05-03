package Connect4.gridComponent.DBComponent

import Connect4.gridComponent.GridInterface

trait DAO {
  def create():Unit
  def read():GridInterface
  def update(grid:GridInterface):Unit
  def delete():Unit
}
