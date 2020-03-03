package com.kaspazza.files

import com.kaspazza.terminal.FileException

class File(override val parentPath: String, override val name: String, val contents: String) extends DirEntry(parentPath, name){
  def setContents(newContents: String): File = new File(parentPath, name, newContents)

  def appendContents(newContents: String): File = setContents(contents + "\n" + newContents)

  override def asDirectory: Directory = throw new FileException("A file cannot be converted to a directory")

  override def getType: String = "File"

  def asFile: File = this

  def isDirectory: Boolean = false

  def isFile: Boolean = true
}

object File {

  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}