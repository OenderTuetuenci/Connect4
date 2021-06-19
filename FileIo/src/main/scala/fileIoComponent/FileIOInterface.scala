package fileIoComponent

/**
 * Trait for FileIO
 */
trait FileIOInterface {
  /**
   * Loads Gamestate from Json
   * @return Tuple with the current Gamestate and who´s turn it is
   */
  def load: (String, Int)

  /**
   * Saving Gamestate to Json
   * @param json string represantation of Gamestate
   * @param player who´s turn is it
   */
  def save(json: String, player: Int): Unit
}
