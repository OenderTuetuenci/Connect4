import com.google.inject.AbstractModule
import controller.ControllerInterface
import model.GridInterface
import model.gridComponent.Grid
import net.codingwell.scalaguice.ScalaModule

class Connect4Module extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controller.controllerComponent.Controller]
  }
}
