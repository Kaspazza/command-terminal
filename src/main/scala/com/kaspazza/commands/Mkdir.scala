package com.kaspazza.commands

import com.kaspazza.files.{DirEntry, Directory}
import com.kaspazza.terminal.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.workingDirectory.path, name)
}
