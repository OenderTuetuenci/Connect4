package Connect4.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import Connect4.model.fileIoComponent.FileIOInterface
import Connect4.model.gridComponent.{Grid, GridInterface}
import play.api.libs.json._

import scala.io.Source

class FileIo extends FileIOInterface{
  override def load: (GridInterface,Int) = {
    var grid:GridInterface = new Grid()
    val source: String = Source.fromFile("connect4.json").getLines.mkString
    val json:JsValue = Json.parse(source)
    val player:Int = (json \\"player").head.as[Int]
    for(index <- 0 until 42){
      val idx = (json \\ "index")(index).as[Int]
      val value = (json \\ "val")(index).as[Int]
      grid = grid.set(idx,value)
    }
    (grid,player)
  }

  override def save(grid: GridInterface,player:Int): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("connect4.json"))
    pw.write(Json.prettyPrint(gridToJson(grid,player)))
    pw.close()
  }
  def gridToJson(grid: GridInterface, player: Int):JsObject ={
    Json.obj(
      "grid"->Json.obj(
        "cells"->Json.toJson( for{
          index <- 0 until 41
        }yield{
          Json.obj(
            "index"->index,
            "val"->grid.grid(index)
          )
        }),
        "player"->JsNumber(player)
      )
    )
  }
}
