package com.lumera.wordsearch.command;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ProcessorType;
import com.lumera.wordsearch.processor.MaxLengthProcessor;
import com.lumera.wordsearch.processor.Processor;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParseResult;

@Command(name = "search", version = "1.0.0", description = "Searching for matching words", mixinStandardHelpOptions = true, header = "Search command", optionListHeading = "%nOptions are: %n")
public class SearchCommand {

  public static EnumMap<ProcessorType, Processor> processorTypeMap = new EnumMap<>(
      ProcessorType.class);

  static {
    // add element to map
    processorTypeMap.put(ProcessorType.MAXLENGTH, new MaxLengthProcessor());
  }

  public static int run(ParseResult parseResult) {
    Integer helpExitCode = CommandLine.executeHelpRequest(parseResult);
    if (helpExitCode != null) {
      return helpExitCode;
    }

    // implement the business logic
    List<CmdOptionConfig> cmdOptionConfigs = WordSearchApplication.xmlConfig.getCmdOptionConfigs();
    cmdOptionConfigs.forEach(cmdOptionConfig -> Optional.ofNullable(
            parseResult.matchedOptionValue(cmdOptionConfig.getName(), null))
        .ifPresent(option -> processorTypeMap
            .get(ProcessorType.valueOf(cmdOptionConfig.getProcessorType()))
            .search(option, "")));

    return 1;
  }
}