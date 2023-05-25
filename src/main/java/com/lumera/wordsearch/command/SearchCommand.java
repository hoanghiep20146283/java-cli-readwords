package com.lumera.wordsearch.command;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.exception.FileInputInvalidException;
import com.lumera.wordsearch.exception.ProcessorNotFoundException;
import com.lumera.wordsearch.service.ProcessorHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParseResult;

@Slf4j
@Command(name = "search", version = "1.0.0", description = "Searching for matching words", mixinStandardHelpOptions = true, header = "Search command", optionListHeading = "%nOptions are: %n")
public final class SearchCommand {

  private SearchCommand() {
  }

  public static int run(ParseResult parseResult) {
    final Set<String> matchingWords = new HashSet<>();

    // Implement the search logic

    // Read Options from command line
    ProcessorHelper.setOptions(parseResult);
    final List<String> matchedOptionNames = parseResult.matchedOptions().stream()
        .flatMap(optionSpec -> Arrays.stream(optionSpec.names())).collect(Collectors.toList());
    final List<CmdOptionConfig> matchedCmdOptionConfigs = WordSearchApplication.xmlConfig.getCmdOptionConfigs()
        .stream().filter(cmdOptionConfig -> matchedOptionNames.contains(cmdOptionConfig.getName()))
        .collect(Collectors.toList());

    // Read thourgh input file
    final String inputFileName = parseResult.matchedOptionValue("file", "wordlist1.txt");

    try (FileInputStream inputStream = new FileInputStream(inputFileName); Scanner sc = new Scanner(
        inputStream, StandardCharsets.UTF_8)) {
      while (sc.hasNextLine()) {
        final String line = sc.nextLine();
        if (line != null) {
          final List<String> matchedWords = searchWord(line.trim(), matchedCmdOptionConfigs);
          matchingWords.addAll(matchedWords);
        }
      }

      // Print out the result
      System.out.println("Matching words: " + matchingWords.size());
    } catch (IOException ex) {
      // Print to the console error messages, not the entire stack trace
      System.out.println("Error when reading input file: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
    return 1;
  }

  private static List<String> searchWord(String word,
      List<CmdOptionConfig> matchedCmdOptionConfigs) {

    if (matchedCmdOptionConfigs.isEmpty()) {
      return Collections.singletonList(word);
    }

    final Set<String> matchedElements = new HashSet<>(
        ProcessorHelper.processorTypeMap.get(matchedCmdOptionConfigs.get(0).getProcessorType())
            .search(word));

    for (int i = 1; i < matchedCmdOptionConfigs.size(); i++) {
      matchedElements.retainAll(
          ProcessorHelper.processorTypeMap.get(matchedCmdOptionConfigs.get(i).getProcessorType())
              .search(word));
    }
    return new ArrayList<>(matchedElements);
  }
}

