package model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import model.fileIoComponent.FileIOInterface
import model.gridComponent.{Grid, GridInterface}
import play.api.libs.json._

import scala.io.Source

class FileIo extends FileIOInterface{
  override def load: (GridInterface,Int) = {
    val grid:GridInterface = new Grid()
    val source: String = Source.fromFile("connect4.json").getLines.mkString
    val json:JsValue = Json.parse(source)
    val player:Int = (json \\"player").head.as[Int]
    for(index <-0 until 6*7){
      val row = (json \\ "row")(index).as[Int]
      val col = (json \\ "col")(index).as[Int]
      val value = (json \\ "val")(index).as[Int]
      grid.set(row,col,value)
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
          row <- 0 until 6
          col <- 0 until 7
        }yield{
          Json.obj(
            "row"->row,
            "col"->col,
            "val"->grid.grid(row)(col)
          )
        }),
        "player"->JsNumber(player)
      )
    )
  }
}
