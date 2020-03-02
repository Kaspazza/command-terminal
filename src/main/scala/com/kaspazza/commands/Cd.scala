package com.kaspazza.commands
import com.kaspazza.files.{DirEntry, Directory}
import com.kaspazza.terminal.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    val root = state.root
    val workingDirectory = state.workingDirectory
    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (workingDirectory.isRoot) workingDirectory.path + dir
      else workingDirectory.path + Directory.SEPARATOR + dir

    val destinationDirectory = doFindEntry(root, absolutePath)

    if (destinationDirectory == null || !destinationDirectory.isDirectory) {
      state.setMessage(dir + ": no such directory")
    } else {
      State(root, destinationDirectory.asDirectory)
    }
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def navigate(currentDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else navigate(nextDir.asDirectory, path.tail)
      }
    }

    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if(path.isEmpty) result
      else if(".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if("..".equals(path.head)){
        if(result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      } else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    val newTokens = collapseRelativeTokens(tokens, List())
    if(newTokens == null) null
    else navigate(root, newTokens)
  }
}
