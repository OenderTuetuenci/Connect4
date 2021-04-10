package Connect4.model.fileIoComponent

import Connect4.model.gridComponent.GridInterface

trait FileIOInterface {

  def load: (GridInterface,Int)
  def save(grid: GridInterface,player:Int): Unit

}
