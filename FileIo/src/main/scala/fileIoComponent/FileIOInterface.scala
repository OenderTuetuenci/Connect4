package fileIoComponent

import Connect4.gridComponent.GridInterface

trait FileIOInterface {

  def load: (GridInterface, Int)

  def save(grid: GridInterface, player: Int): Unit

}
