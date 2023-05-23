package com.lumera.wordsearch.command;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "hello",
    description = "Says hello"
)
public class SearchCommand implements Runnable {



  @Override
  public void run() {
    System.out.println("Search command!");
  }
}