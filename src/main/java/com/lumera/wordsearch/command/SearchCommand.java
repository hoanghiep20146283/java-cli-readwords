package com.lumera.wordsearch.command;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "search", version = "1.0.0", description = "Searching for matching words", mixinStandardHelpOptions = true, header = "Search command", optionListHeading = "%nOptions are: %n")
public class SearchCommand implements Runnable {

  @Option(names = {"-h", "--help"}, usageHelp = true, description = "Help")
  boolean help;


  @Override
  public void run() {
    System.out.println("Search command!");
  }
}