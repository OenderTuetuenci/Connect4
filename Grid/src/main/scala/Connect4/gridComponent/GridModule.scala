package Connect4.gridComponent

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class GridModule extends AbstractModule with ScalaModule{
  override def configure(): Unit ={
    bind[GridInterface].toInstance(Grid())
  }
}
