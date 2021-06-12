package dev.insideyou.connect4_scala3.controller

import dev.insideyou.connect4_scala3.utils.Command

class MoveCommand(column:Int,player:Int,controller:Controller) extends Command:
    var index:Int = -1
    override def doStep(): Unit =
        controller.grid.put(column,player) match
            case Some(value) => index = value._1
                                controller.grid = value._2
                                update()
            case None => controller.publish(new blockedColumnEvent)

    override def undoStep(): Unit = 
        controller.grid = controller.grid.set(index,0)
        controller.setPlayer(player)
        controller.publish(updateGridEvent(index,0))

    override def redoStep(): Unit =
        controller.grid = controller.grid.set(index,player)
        controller.nextPlayer()
        update()
    private def update(): Unit =
        val end: Boolean = controller.checkForWinner()
        if(!end)
            controller.nextPlayer()
            controller.publish(updateGridEvent(index,player))
        else 
            controller.publish(updateGridEvent(index,player))
            controller.publish(new endGameEvent)