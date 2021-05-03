package Connect4.gridComponent

import Connect4.gridComponent.DBComponent.DAO
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class GridModule extends AbstractModule with ScalaModule{
  override def configure(): Unit ={
    bind[GridInterface].toInstance(Grid())
    bind[DAO].to[DBComponent.MySqlImpl.MySql]
  }
}
