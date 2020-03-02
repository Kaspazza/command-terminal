package com.kaspazza.commands

import com.kaspazza.terminal.State

trait Command {

  def apply(state: State): State
}

object Command {

  def from(input: String): Command = new UnknownCommand
}