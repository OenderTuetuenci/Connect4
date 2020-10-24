package model.fileIoComponent

import model.gridComponent.GridInterface

trait FileIOInterface {

  def load: (GridInterface,Int)
  def save(grid: GridInterface,player:Int): Unit

}
