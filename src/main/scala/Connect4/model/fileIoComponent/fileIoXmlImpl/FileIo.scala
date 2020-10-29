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
      val row = (cell\ "row").text.trim.toInt
      val col = (cell\ "col").text.trim.toInt
      val value = (cell\ "val").text.trim.toInt
      grid.grid(row)(col) = value
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
      row <- 0 until 6
      col <-0 until 7
      } yield {
      <cell>
        <row>{row}</row>
        <col>{col}</col>
        <val>{grid.grid(row)(col)}</val>
      </cell>
    }
      }</grid>
      <player>{player}</player>
    </game>
  }

}
