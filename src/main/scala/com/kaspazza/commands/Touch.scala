package com.kaspazza.commands
import com.kaspazza.files.{DirEntry, File}
import com.kaspazza.terminal.State

class Touch(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.workingDirectory.path, name)
}
