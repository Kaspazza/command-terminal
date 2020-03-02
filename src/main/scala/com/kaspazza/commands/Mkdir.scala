package com.kaspazza.commands

import com.kaspazza.files.Directory
import com.kaspazza.terminal.State

class Mkdir(name: String) extends Command {

  def checkIllegal(name: String): Boolean = name.contains(".")

  def doMkdir(state: State, name: String): State = {
    def updateStructure(directory: Directory, path: List[String], newEntry: Directory): Directory = {
      if (path.isEmpty) directory.addEntry(newEntry)
      else {
        val oldEntry = directory.findEntry(path.head)
        directory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail, newEntry))
      }
    }

    val workingDirectory = state.workingDirectory
    val allDirsInPath = workingDirectory.getAllFoldersInPath
    val newDir = Directory.empty(workingDirectory.path, name)
    val newRoot = updateStructure(state.root, allDirsInPath, newDir)
    val newWorkingDirectory = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWorkingDirectory)
  }

  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory
    if(workingDirectory.hasEntry(name)){
      state.setMessage("Entry " + name + " already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators!")
    } else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name!")
    } else {
      doMkdir(state, name)
    }
  }
}
