package fileIoComponent

trait FileIOInterface {

  def load: (String, Int)

  def save(json: String, player: Int): Unit

}
