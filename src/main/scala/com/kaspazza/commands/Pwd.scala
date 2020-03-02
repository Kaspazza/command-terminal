package com.kaspazza.commands

import com.kaspazza.terminal.State

class Pwd extends Command {
  override def apply(state: State): State = state.setMessage(state.workingDirectory.path)
}
