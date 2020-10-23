import model.{Grid, Player}
import view.TUI
import controller.Controller

val c = new Controller(new Grid)
val t = new TUI(c)

//println(c.showGrid())
//t.printGrid()
