package com.lumera.wordsearch.command;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ProcessorType;
import com.lumera.wordsearch.exception.FileInputInvalidException;
import com.lumera.wordsearch.processor.MaxLengthProcessor;
import com.lumera.wordsearch.processor.MinLengthProcessor;
import com.lumera.wordsearch.processor.Processor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParseResult;

@Command(name = "search", version = "1.0.0", description = "Searching for matching words", mixinStandardHelpOptions = true, header = "Search command", optionListHeading = "%nOptions are: %n")
public class SearchCommand {

  public static EnumMap<ProcessorType, Processor> processorTypeMap = new EnumMap<>(
      ProcessorType.class);

  static {
    // add processors to map (prototype pattern)
    processorTypeMap.put(ProcessorType.MAXLENGTH, new MaxLengthProcessor());
    processorTypeMap.put(ProcessorType.MINLENGTH, new MinLengthProcessor());
  }

  public static int run(ParseResult parseResult) {
    Set<String> matchingWords = new HashSet<>();
    //Handle errors occurs while processing the help request
    Integer helpExitCode = CommandLine.executeHelpRequest(parseResult);
    if (helpExitCode != null) {
      return helpExitCode;
    }

    // implement the search logic
    // Read thourgh input file
    String inputFileName = parseResult.matchedOptionValue("file", "wordlist.txt");

    try (FileInputStream inputStream = new FileInputStream(inputFileName); Scanner sc = new Scanner(
        inputStream, StandardCharsets.UTF_8)) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if (line != null && searchWord(parseResult, line.trim())) {
          matchingWords.add(line.trim());
        }
      }
      System.out.print("Matching words: " + matchingWords.size());
    } catch (IOException ex) {
      throw new FileInputInvalidException(ex);
    }
    return 1;
  }

  private static boolean searchWord(ParseResult parseResult, String word) {
    List<CmdOptionConfig> cmdOptionConfigs = WordSearchApplication.xmlConfig.getCmdOptionConfigs();
    return cmdOptionConfigs.stream().allMatch(cmdOptionConfig ->
        Optional.ofNullable(
                parseResult.matchedOptionValue(cmdOptionConfig.getName(), null))
            .map(option ->
                processorTypeMap
                    .get(cmdOptionConfig.getProcessorType())
                    .search(option, word)
            ).orElse(true)
    );
  }
}
