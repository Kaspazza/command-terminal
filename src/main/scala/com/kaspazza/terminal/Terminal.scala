package com.kaspazza.terminal

import java.util.Scanner

import com.kaspazza.commands.Command
import com.kaspazza.files.Directory

object Terminal extends App {

  val root = Directory.ROOT
  val initialState = State(root, root)
  initialState.show
  io.Source.stdin.getLines().foldLeft(initialState)((currentState, newLine) => {
    val newState = Command.from(newLine).apply(currentState)
    newState.show
    newState
  })
}
