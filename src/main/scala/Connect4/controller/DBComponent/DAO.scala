package Connect4.controller.DBComponent

import scala.concurrent.Future

trait DAO {
  def create():Future[Any]
  def read():Int
  def update(player:Int):Future[Any]
  def delete():Future[Any]
}
