package com.lumera.wordsearch.command;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.processor.ClasifyProcessor;
import com.lumera.wordsearch.processor.ContainsOnlyProcessor;
import com.lumera.wordsearch.processor.EndsWithProcessor;
import com.lumera.wordsearch.processor.MaxLengthProcessor;
import com.lumera.wordsearch.processor.MinLengthProcessor;
import com.lumera.wordsearch.processor.StartsWithProcessor;
import com.lumera.wordsearch.service.ProcessorHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParseResult;

/**
 * This class apply Strategy Design Pattern: allows SearchCommand to define a different set of {@link com.lumera.wordsearch.processor.Processor}
 * and encapsulate their processing logic into separate classes. These algorithms and {@link com.lumera.wordsearch.processor.Processor} can
 * change their behavior dynamically during runtime and can be used interchangeably and in
 * combination without affecting interaction with other objects.
 *
 * @author James Hoang
 * @see com.lumera.wordsearch.processor.Processor
 */
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
    final List<CmdOptionConfig> matchedCmdOptionConfigs =
        WordSearchApplication.xmlConfig
            .getCmdOptionConfigs()
            .stream()
            .filter(cmdOptionConfig -> matchedOptionNames.contains(cmdOptionConfig.getName()))
            .collect(Collectors.toList());

    // Read thourgh input file
    final String inputFileName = parseResult.matchedOptionValue("file", "wordlist.txt");

    try (FileInputStream inputStream = new FileInputStream(inputFileName); Scanner sc = new Scanner(
        inputStream, StandardCharsets.UTF_8)) {
      while (sc.hasNextLine()) {
        final String line = sc.nextLine();
        if (line != null) {
          final List<String> matchedWords = ProcessorHelper.searchWord(line.trim(),
              matchedCmdOptionConfigs);
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


}

