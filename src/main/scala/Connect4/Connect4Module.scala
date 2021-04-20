package Connect4

import Connect4.gridComponent.{Grid, GridInterface}
import controller.controllerComponent._
import com.google.inject.AbstractModule
import fileIoComponent.FileIOInterface
import fileIoComponent.fileIoJsonImpl.FileIo
import net.codingwell.scalaguice.ScalaModule

class Connect4Module extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[GridInterface].toInstance(Grid())
    bind[ControllerInterface].to[controller.controllerComponent.Controller]
    bind[FileIOInterface].to[fileIoComponent.fileIoJsonImpl.FileIo]
    //bind[FileIOInterface].to[fileIoComponent.fileIoXmlImpl.FileIo]
  }
}
