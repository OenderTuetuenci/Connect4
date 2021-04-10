package Connect4

import Connect4.controller.ControllerInterface
import model.fileIoComponent.FileIOInterface
import model.gridComponent.{Grid, GridInterface}
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class Connect4Module extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[GridInterface].toInstance(Grid())
    bind[ControllerInterface].to[controller.controllerComponent.Controller]
    //bind[FileIOInterface].to[Connect4.model.fileIoComponent.fileIoJsonImpl.FileIo]
    bind[FileIOInterface].to[model.fileIoComponent.fileIoXmlImpl.FileIo]
  }
}
