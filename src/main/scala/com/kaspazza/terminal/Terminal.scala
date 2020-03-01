package com.kaspazza.terminal

import java.util.Scanner

object Terminal extends App {
  val scanner = new Scanner(System.in)

  while(true){
    print("$ ")
    println(scanner.nextLine())
  }
}
