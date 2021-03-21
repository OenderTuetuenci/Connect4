package Connect4.model.fileIoComponent.fileIoXmlImpl
import Connect4.model.fileIoComponent.FileIOInterface
import Connect4.model.gridComponent.{Grid, GridInterface}

import scala.xml.{Elem, PrettyPrinter}

class FileIo extends FileIOInterface{
  override def load: (GridInterface, Int) = {
    val grid:GridInterface = new Grid()
    val file = scala.xml.XML.loadFile("connect4.xml")
    val player = (file \\ "player").text.trim.toInt
    val gridNodes = file \\ "grid"\\"cell"
    for(cell<-gridNodes){
      val index = (cell\ "index").text.trim.toInt
      val value = (cell\ "val").text.trim.toInt
      grid.set(index,value)
    }
    (grid,player)
  }

  override def save(grid: GridInterface, player: Int): Unit ={
    import java.io._
    val pw = new PrintWriter(new File("connect4.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gridToXml(grid, player))
    pw.write(xml)
    pw.close()
  }

  def gridToXml(grid: GridInterface, player: Int):Elem={
    <game>
    <grid>{for{
      index <- 0 to 41
      } yield {
      <cell>
        <index>{index}</index>
        <val>{grid.grid(index)}</val>
      </cell>
    }
      }</grid>
      <player>{player}</player>
    </game>
  }

}
