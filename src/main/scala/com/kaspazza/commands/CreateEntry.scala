package com.kaspazza.commands

import com.kaspazza.files.{DirEntry, Directory}
import com.kaspazza.terminal.State

abstract class CreateEntry(name: String) extends Command {
  def checkIllegal(name: String): Boolean = name.contains(".")

  def createEntry(state: State, name: String): State = {
    def updateStructure(directory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) directory.addEntry(newEntry)
      else {
        val oldEntry = directory.findEntry(path.head)
        directory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail, newEntry))
      }
    }

    val workingDirectory = state.workingDirectory
    val allDirsInPath = workingDirectory.getAllFoldersInPath
    val newEntry: DirEntry = createSpecificEntry(state)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    val newWorkingDirectory = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWorkingDirectory)
  }

  def createSpecificEntry(state: State): DirEntry

  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory
    if(workingDirectory.hasEntry(name)){
      state.setMessage("Entry " + name + " already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators!")
    } else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name!")
    } else {
      createEntry(state, name)
    }
  }
}
