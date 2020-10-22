import model.Grid

object Connect4 {
  def main(args: Array[String]): Unit = {
    val test = new Grid
    test.put(1,1)
    test.put(1,1)
    print(test.toString)
  }
}
