package Connect4.controller.DBComponent.MongoDBImpl

import Connect4.controller.DBComponent.DAO
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.{InsertOneResult, UpdateResult}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observer, SingleObservable}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

class MongoDB extends DAO{
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val uri: String = "mongodb://localhost:27017"
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("connect4")

  val playerCollection:MongoCollection[Document] = database.getCollection("Player")

  override def create(): Future[Any] = playerCollection.insertOne(Document("_id"->0,"player"->0)).toFuture()

  override def read(): Int = Await.result(playerCollection.find(equal("_id", 0)).first().head(), atMost = 10.second)("player").asInt32().getValue

  override def update(player: Int): Future[Any] = playerCollection.updateOne(equal("_id",0),set("player",player)).toFuture()

  override def delete(): Future[Any] = playerCollection.drop().toFuture()
}
