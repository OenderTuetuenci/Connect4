import com.google.inject.AbstractModule
import controller.controllerComponent.ControllerInterface
import model.fileIoComponent.FileIOInterface
import model.gridComponent.{Grid, GridInterface}
import net.codingwell.scalaguice.ScalaModule

class Connect4Module extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controller.controllerComponent.Controller]
    //bind[FileIOInterface].to[model.fileIoComponent.fileIoJsonImpl.FileIo]
    bind[FileIOInterface].to[model.fileIoComponent.fileIoXmlImpl.FileIo]
  }
}
