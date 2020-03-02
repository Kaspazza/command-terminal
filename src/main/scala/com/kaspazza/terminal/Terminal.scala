package com.kaspazza.terminal

import java.util.Scanner

import com.kaspazza.commands.Command
import com.kaspazza.files.Directory

object Terminal extends App {

  val root = Directory.ROOT
  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState, newLine) => {
    currentState.show
    Command.from(newLine).apply(currentState)
  })
}
