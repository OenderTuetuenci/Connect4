package Connect4.gridComponent.DBComponent.MongoDBImpl

import Connect4.gridComponent.DBComponent.DAO
import Connect4.gridComponent.{GridInterface, GridModule}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.google.inject.{Guice, Injector}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.{InsertOneResult, UpdateResult}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observer, SingleObservable}
import play.api.libs.json.{JsObject, Json}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor}

class MongoDB extends DAO{
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val injector: Injector = Guice.createInjector(new GridModule)
  val uri: String = "mongodb://localhost:27017"
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("connect4")
  val gridCollection:MongoCollection[Document] = database.getCollection("Grid")

  override def create(): Unit = handleObserverInsertion(gridCollection.insertOne(Document("_id"->0,"grid" ->"")))

  override def read(): GridInterface = {
    var grid: GridInterface = injector.getInstance(classOf[GridInterface])
    val result = Await.result(gridCollection.find(equal("_id", 0)).first().head(), atMost = 10.second)("grid").asString().getValue
    grid = grid.rebuild(result)
    grid
  }

  override def update(grid: GridInterface): Unit = handleObserverUpdate(gridCollection.updateOne(equal("_id",0),set("grid",gridToJson(grid).toString())))

  override def delete(): Unit = gridCollection.drop()


  private def handleObserverInsertion(insertObservable: SingleObservable[InsertOneResult]): Unit = {
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = println(s"onError: $e")

      override def onComplete(): Unit = println("completed")
    })
  }
  private def handleObserverUpdate(insertObservable: SingleObservable[UpdateResult]): Unit = {
    insertObservable.subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = println(s"onError: $e")

      override def onComplete(): Unit = println("completed")
    })
  }
  private def gridToJson(grid: GridInterface): JsObject = {
    Json.obj(
      "grid" -> Json.obj(
        "cells" -> Json.toJson(for {
          index <- 0 until 42
        } yield {
          Json.obj(
            "index" -> index,
            "val" -> grid.grid(index)
          )
        })
      )
    )
  }
}
