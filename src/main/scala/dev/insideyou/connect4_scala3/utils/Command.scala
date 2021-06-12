package dev.insideyou.connect4_scala3.utils

trait Command:
  def doStep(): Unit
  def undoStep(): Unit
  def redoStep(): Unit
