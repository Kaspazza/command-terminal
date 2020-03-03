package com.kaspazza.commands
import com.kaspazza.files.Directory
import com.kaspazza.terminal.State

class Rm(name: String) extends Command {
  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory
    val absolutePath =
      if(name.startsWith(Directory.SEPARATOR)) name
      else if(workingDirectory.isRoot) workingDirectory.path + name
      else workingDirectory.path + Directory.SEPARATOR + name
    if(Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("not supported yet")
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, path: String): State = {
    def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if(!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if(newNextDirectory == nextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }
    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)

    if(newRoot == state.root)
      state.setMessage(path + ": no such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.workingDirectory.path.substring(1)))
  }
}
