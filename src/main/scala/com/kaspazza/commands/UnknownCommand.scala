package com.kaspazza.commands

import com.kaspazza.terminal.State

class UnknownCommand extends Command {

  override def apply(state: State): State = state.setMessage("Command not found!")
}
