package Connect4.controller.DBComponent.MongoDBImpl

import Connect4.controller.DBComponent.DAO
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.{InsertOneResult, UpdateResult}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observer, SingleObservable}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor}

class MongoDB extends DAO{
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val uri: String = "mongodb://localhost:27017"
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("connect4")

  val playerCollection:MongoCollection[Document] = database.getCollection("Player")

  override def create(): Unit = handleObserverInsertion(playerCollection.insertOne(Document("_id"->0,"player"->0)))

  override def read(): Int = Await.result(playerCollection.find(equal("_id", 0)).first().head(), atMost = 10.second)("player").asInt32().getValue

  override def update(player: Int): Unit = handleObserverUpdate(playerCollection.updateOne(equal("_id",0),set("player",player)))

  override def delete(): Unit = playerCollection.drop()

  private def handleObserverInsertion(insertObservable: SingleObservable[InsertOneResult]): Unit = {
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = {}//println(s"onError: $e")

      override def onComplete(): Unit = println("completed")
    })
  }
  private def handleObserverUpdate(insertObservable: SingleObservable[UpdateResult]): Unit = {
    insertObservable.subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = {}//println(s"onError: $e")

      override def onComplete(): Unit = println("completed")
    })
  }
}
