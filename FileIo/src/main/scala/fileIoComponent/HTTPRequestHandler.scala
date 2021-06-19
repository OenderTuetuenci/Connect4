package fileIoComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.{Get, Post}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.google.inject.{Guice, Injector}

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class HTTPRequestHandler{
  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext
  val injector: Injector = Guice.createInjector(new FileIoModule)

  def getJson:String = {
    val response = Http().singleRequest(Get("http://grid:8080/model/grid"))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }

  def load(json:String): Unit = {
    val response = Http().singleRequest(Post("http://grid:8080/model/grid/rebuild?json="+json))
    val jsonFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    Await.result(jsonFuture, Duration(10, TimeUnit.SECONDS))
  }
}
