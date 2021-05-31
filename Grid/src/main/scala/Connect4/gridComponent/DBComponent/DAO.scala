package Connect4.gridComponent.DBComponent

import Connect4.gridComponent.GridInterface

import scala.concurrent.Future

trait DAO {
  def create():Unit
  def read():Future[GridInterface]
  def update(grid:GridInterface):Unit
  def delete():Future[Any]
}
