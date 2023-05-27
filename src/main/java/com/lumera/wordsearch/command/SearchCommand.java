package com.lumera.wordsearch.command;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ExitCode;
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
import picocli.CommandLine.ExecutionException;
import picocli.CommandLine.ParseResult;

@Slf4j
@Command(name = "search", version = "1.0.0", description = "Searching for matching words", mixinStandardHelpOptions = true, header = "Search command", optionListHeading = "%nOptions are: %n")
public final class SearchCommand {

  private SearchCommand() {
  }

  /**
   * @return {@link ExitCode#SUCCESS} if the command completes execution without any problems,
   * otherwise it returns {@link ExitCode#FAILURE}
   * @throws ExecutionException if problem occurs while calling search command, which will be
   *                            handled by
   *                            {@link com.lumera.wordsearch.service.PrintExceptionMessageHandler}
   * @author James Hoang
   * @see ExitCode {@code classArgument}, otherwise {@code false}
   * @see ExecutionException
   */
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
    final String inputFileName = parseResult.matchedOptionValue("file", "wordlist1.txt");

    try (FileInputStream inputStream = new FileInputStream(
        inputFileName); Scanner sc = new Scanner(
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
    } catch (IOException ioException) {
      WordSearchApplication.commandLine.printVersionHelp(System.out);
      throw new ExecutionException(WordSearchApplication.commandLine,
          "Error Reading input file (" + "search" + "): " + ioException, ioException);
    }
    return ExitCode.SUCCESS.getExitCode();
  }
}

