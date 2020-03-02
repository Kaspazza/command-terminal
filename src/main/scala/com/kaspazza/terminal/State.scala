package com.kaspazza.terminal

import com.kaspazza.files.Directory

class State(val root: Directory, val workingDirectory: Directory, val output: String) {

  def show: Unit = {
    println(output)
    print(State.TERMINAL_TOKEN)
  }

  def setMessage(message: String): State = State(root, workingDirectory, message)
}

object State {
  val TERMINAL_TOKEN = "$ "

  def apply(root: Directory, workingDirectory: Directory, output: String = ""): State =
    new State(root, workingDirectory, output)
}