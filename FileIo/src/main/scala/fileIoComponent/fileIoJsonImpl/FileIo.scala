package fileIoComponent.fileIoJsonImpl

import fileIoComponent.FileIOInterface
import play.api.libs.json._
import scala.io.Source

class FileIo extends FileIOInterface{
  override def load: (String,Int) = {
    val source: String = Source.fromFile("connect4.json").getLines.mkString
    val json:JsValue = Json.parse(source)
    val player:Int = (json \\"player").head.as[Int]
    (source,player)
  }

  override def save(json: String,player:Int): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("connect4.json"))
    val json2 = Json.parse(json)
    pw.write(Json.prettyPrint(json2))
    pw.close()
  }
}
