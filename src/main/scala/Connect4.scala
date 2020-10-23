import model.Grid

object Connect4 {
  def main(args: Array[String]): Unit = {
    val test = new Grid
    test.put(1,2)
    test.put(2,1)
    test.put(2,2)
    test.put(3,1)
    test.put(3,2)
    test.put(3,1)
    test.put(4,1)
    test.put(4,1)
    test.put(4,1)
    test.put(4,2)
    test.put(2,1)
    test.put(1,1)
    test.put(1,2)
    test.put(1,1)
    test.put(1,1)
    test.put(1,1)
    test.put(5,1)
    test.put(5,1)
    test.put(5,1)
    test.put(5,1)
    test.put(5,2)
    //test.put(0,1)
    //test.put(2,1)
    //test.put(3,1)
    //test.put(1,2)
    //test.put(1,2)
    //test.put(1,2)
    println(test.toString)
    println(test.checkConnect4((5,1),2))
  }
}
