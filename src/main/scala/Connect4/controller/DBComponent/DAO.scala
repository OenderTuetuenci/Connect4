package Connect4.controller.DBComponent

trait DAO {
  def create():Unit
  def read():Int
  def update(player:Int):Unit
  def delete():Unit
}
