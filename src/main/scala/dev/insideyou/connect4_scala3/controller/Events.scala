package dev.insideyou.connect4_scala3.controller

import scala.swing.event.Event

class startGameEvent extends Event
class updateGridEvent(stone:Int,player:Int) extends Event
class blockedColumnEvent extends Event
class endGameEvent extends Event
class saveGameEvent extends Event
class updateAllGridEvent extends Event
