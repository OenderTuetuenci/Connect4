package Connect4

import Connect4.controller.DBComponent.DAO
import Connect4.controller.controllerComponent._
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class Connect4Module extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.Controller]
    bind[DAO].to[controller.DBComponent.MongoDBImpl.MongoDB]
  }
}
