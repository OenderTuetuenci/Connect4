package fileIoComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.google.inject.{Guice, Injector}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json._

import scala.io.StdIn

object FileIoServer extends PlayJsonSupport{

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext

  def main(args: Array[String]): Unit = {
    val injector: Injector = Guice.createInjector(new FileIoModule)
    val fileIo:FileIOInterface = injector.getInstance(classOf[FileIOInterface])
    val requestHandler = new HTTPRequestHandler()

    val route = concat (
      path("fileIo" / "save"){
        parameters("player".optional) { player =>
          val player2 = player.getOrElse("")
          if (player2 == "") {
            complete(69)
          } else {
            val json = requestHandler.getJson
            fileIo.save(json,player2.toInt)
            complete(420)
          }
        }
      },
      path("fileIo" / "load") {
        post{
          val stats = fileIo.load
          val json = Json.parse(stats._1)
          val grid = (json \\ "grid")
          val result = requestHandler.load(json.toString())
          complete(stats._2)
        }
      },
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8081).bind(route)

    println(s"Server online at http://0.0.0.0:8081/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
