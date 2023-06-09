package com.lumera.wordsearch.command;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ExitCode;
import com.lumera.wordsearch.exception.ProcessorException;
import com.lumera.wordsearch.processor.Processor;
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
   * This class applies  Bridge Design Pattern: define a
   * {@link com.lumera.wordsearch.processor.CombineProcessor} at runtime to demonstrate logic
   * between multiple processors multiple processor. Making 2 layer of abstractions:
   * {@link com.lumera.wordsearch.processor.CombineProcessor} and {@link Processor} <br> Prefer
   * composition to inheritance: it's easier to modify the logic of each processor as well as the
   * logic that aggregates the final result between processors, at a later stage <br>
   *
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
    final String inputFileName = parseResult.matchedOptionValue("file", "wordlist.txt");

    try (FileInputStream inputStream = new FileInputStream(
        inputFileName); Scanner sc = new Scanner(
        inputStream, StandardCharsets.UTF_8)) {
      while (sc.hasNextLine()) {
        final String line = sc.nextLine();
        if (line != null) {
          final List<String> matchedWords = ProcessorHelper.combineProcessorTypeMap.get(
                  WordSearchApplication.xmlConfig.getCombineProcessorType())
              .combine(line.trim(), matchedCmdOptionConfigs);
          matchingWords.addAll(matchedWords);
        }
      }

      ProcessorHelper.printResult(matchingWords);
    } catch (IOException ioException) {
      log.error(ioException.getMessage(), ioException);
      parseResult.commandSpec().commandLine().printVersionHelp(System.out);
      throw new ExecutionException(parseResult.commandSpec().commandLine(),
          "Error Reading input file (" + "command: search" + "): " + ioException, ioException);
    } catch (ProcessorException processorException) {
      log.error(processorException.getMessage(), processorException);
      parseResult.commandSpec().commandLine().printVersionHelp(System.out);
      throw new ExecutionException(parseResult.commandSpec().commandLine(),
          "ProcessorException Exception (" + "command: search" + "): " + processorException,
          processorException);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      parseResult.commandSpec().commandLine().printVersionHelp(System.out);
      throw new ExecutionException(parseResult.commandSpec().commandLine(),
          "Unknown Exception (" + "command: search" + "): " + ex, ex);
    } finally {
      ProcessorHelper.processorTypeMap.values().forEach(Processor::reset);
    }
    return ExitCode.SUCCESS.getExitCode();
  }
}

