package com.lumera.wordsearch.command;

import static com.google.common.base.Preconditions.checkNotNull;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ProcessorType;
import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.exception.FileInputInvalidException;
import com.lumera.wordsearch.processor.ClasifyProcessor;
import com.lumera.wordsearch.processor.ContainsOnlyProcessor;
import com.lumera.wordsearch.processor.EndsWithProcessor;
import com.lumera.wordsearch.processor.MaxLengthProcessor;
import com.lumera.wordsearch.processor.MinLengthProcessor;
import com.lumera.wordsearch.processor.Processor;
import com.lumera.wordsearch.processor.StartsWithProcessor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParseResult;

@Command(name = "search", version = "1.0.0", description = "Searching for matching words", mixinStandardHelpOptions = true, header = "Search command", optionListHeading = "%nOptions are: %n")
public class SearchCommand {

  public static EnumMap<ProcessorType, Processor> processorTypeMap = new EnumMap<>(
      ProcessorType.class);

  public static EnumMap<ProcessorType, Class> defaultValueMap = new EnumMap<>(
      ProcessorType.class);

  static {
    // add processors to map (prototype pattern)
    processorTypeMap.put(ProcessorType.MAXLENGTH, new MaxLengthProcessor());
    processorTypeMap.put(ProcessorType.MINLENGTH, new MinLengthProcessor());
    processorTypeMap.put(ProcessorType.STARTSWITH, new StartsWithProcessor());
    processorTypeMap.put(ProcessorType.ENDSWITH, new EndsWithProcessor());
    processorTypeMap.put(ProcessorType.CONTAINSONLY, new ContainsOnlyProcessor());
    processorTypeMap.put(ProcessorType.CLASS, new ClasifyProcessor());
    defaultValueMap.put(ProcessorType.MAXLENGTH, Long.class);
    defaultValueMap.put(ProcessorType.MINLENGTH, Long.class);
    defaultValueMap.put(ProcessorType.STARTSWITH, String.class);
    defaultValueMap.put(ProcessorType.ENDSWITH, String.class);
    defaultValueMap.put(ProcessorType.CONTAINSONLY, String.class);
    defaultValueMap.put(ProcessorType.CLASS, WordClass.class);
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
    setOptions(parseResult);
    List<String> matchedOptionNames = parseResult.matchedOptions().stream()
        .flatMap(optionSpec -> Arrays.stream(optionSpec.names())).collect(Collectors.toList());
    List<CmdOptionConfig> matchedCmdOptionConfigs = WordSearchApplication.xmlConfig.getCmdOptionConfigs()
        .stream().filter(cmdOptionConfig -> matchedOptionNames.contains(cmdOptionConfig.getName()))
        .collect(Collectors.toList());

    String inputFileName = parseResult.matchedOptionValue("file", "wordlist.txt");

    try (FileInputStream inputStream = new FileInputStream(inputFileName); Scanner sc = new Scanner(
        inputStream, StandardCharsets.UTF_8)) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if (line != null && searchWord(line.trim(), matchedCmdOptionConfigs)) {
          matchingWords.add(line.trim());
        }
      }
      System.out.println("Matching words: " + matchingWords.size());
    } catch (IOException ex) {
      throw new FileInputInvalidException(ex);
    }
    return 1;
  }

  private static boolean searchWord(String word, List<CmdOptionConfig> matchedCmdOptionConfigs) {
    return matchedCmdOptionConfigs.stream().allMatch(
        cmdOptionConfig -> processorTypeMap.get(cmdOptionConfig.getProcessorType()).search(word));
  }

  private static void setOptions(ParseResult parseResult) {
    List<CmdOptionConfig> cmdOptionConfigs = WordSearchApplication.xmlConfig.getCmdOptionConfigs();
    cmdOptionConfigs.forEach(cmdOptionConfig -> Optional.ofNullable(
            parseResult.matchedOptionValue(cmdOptionConfig.getName(),
                getProcessorDefaultValue(cmdOptionConfig.getProcessorType(),
                    defaultValueMap.get(cmdOptionConfig.getProcessorType()))))
        .ifPresent(optionValue ->
            processorTypeMap
                .get(cmdOptionConfig.getProcessorType())
                .setOptionValue(optionValue)
        ));
  }

  /**
   * Returns the default value of {@code type} as defined by JLS --- <br>
   * {@code 0x7fffffffffffffffL (Long.MAX_VALUE)} for
   * {@link com.lumera.wordsearch.processor.MaxLengthProcessor MaxLengthProcessor} <br> {@code 0L}
   * for {@link com.lumera.wordsearch.processor.MinLengthProcessor MinLengthProcessor} <br>
   * {@code false} for {@link  java.lang.Boolean Boolean} <br> {@code empty } for
   * {@link com.lumera.wordsearch.processor.StartsWithProcessor StartsWithProcessor} and
   * {@link com.lumera.wordsearch.processor.EndsWithProcessor EndsWithProcessor} <br> For other
   * types and {@code void}, {@code null} is returned.
   */
  @SuppressWarnings("unchecked")
  @CheckForNull
  public static <T> T getProcessorDefaultValue(ProcessorType processorType, Class<T> type) {
    checkNotNull(type);
    if (type == Boolean.class) {
      return (T) Boolean.FALSE;
    } else if (type == Integer.class) {
      return (T) Integer.valueOf(0);
    } else if (type == Long.class) {
      if (processorType == ProcessorType.MAXLENGTH) {
        return (T) Long.valueOf(Long.MAX_VALUE);
      } else {
        return (T) Long.valueOf(0L);
      }
    } else if (type == String.class) {
      return (T) "";
    } else if (type == WordClass.class) {
      return (T) WordClass.all;
    }
    return null;
  }
}

