package utils

trait Observer{
  def update(e: String): Any

}

class Observable {
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer): Unit = subscribers = subscribers :+ s
  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers(e: String): Unit = subscribers.foreach(o => o.update(e))
}
