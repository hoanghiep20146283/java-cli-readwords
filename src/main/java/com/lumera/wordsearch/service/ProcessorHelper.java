package com.lumera.wordsearch.service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ProcessorType;
import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.processor.ClasifyProcessor;
import com.lumera.wordsearch.processor.ContainsOnlyProcessor;
import com.lumera.wordsearch.processor.EndsWithProcessor;
import com.lumera.wordsearch.processor.MaxLengthProcessor;
import com.lumera.wordsearch.processor.MinLengthProcessor;
import com.lumera.wordsearch.processor.Processor;
import com.lumera.wordsearch.processor.StartsWithProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.CheckForNull;
import lombok.experimental.UtilityClass;
import org.apache.log4j.BasicConfigurator;
import picocli.CommandLine.ParseResult;

/**
 * This class apply Strategy Design Pattern: allows SearchCommand to define a different set of
 * {@link Processor} and encapsulate their processing logic into separate classes. These algorithms
 * and {@link Processor} can change their behavior dynamically during runtime and can be used
 * interchangeably and in combination without affecting interaction with other objects.
 *
 * @author James Hoang
 */
@UtilityClass
public class ProcessorHelper {

  public static final EnumMap<ProcessorType, Processor> processorTypeMap = new EnumMap<>(
      ProcessorType.class);

  public static final EnumMap<ProcessorType, Class> defaultValueMap = new EnumMap<>(
      ProcessorType.class);

  static {
    // Log4j config
    BasicConfigurator.configure();
    // add processors to map (prototype pattern)
    processorTypeMap.put(ProcessorType.MAXLENGTH, new MaxLengthProcessor());
    processorTypeMap.put(ProcessorType.MINLENGTH, new MinLengthProcessor());
    processorTypeMap.put(ProcessorType.STARTSWITH, new StartsWithProcessor());
    processorTypeMap.put(ProcessorType.ENDSWITH, new EndsWithProcessor());
    processorTypeMap.put(ProcessorType.CONTAINSONLY, new ContainsOnlyProcessor());
    defaultValueMap.put(ProcessorType.MAXLENGTH, Long.class);
    defaultValueMap.put(ProcessorType.MINLENGTH, Long.class);
    defaultValueMap.put(ProcessorType.STARTSWITH, String.class);
    defaultValueMap.put(ProcessorType.ENDSWITH, String.class);
    defaultValueMap.put(ProcessorType.CONTAINSONLY, String.class);
    defaultValueMap.put(ProcessorType.CLASS, WordClass.class);
  }

  /**
   * Returns the default value of {@code type} as defined by JLS --- <br>
   * {@code 0x7fffffffffffffffL (Long.MAX_VALUE)} for {@link MaxLengthProcessor MaxLengthProcessor}
   * <br> {@code 0L} for {@link MinLengthProcessor MinLengthProcessor} <br> {@code false} for
   * {@link  Boolean Boolean} <br> {@code empty } for
   * {@link StartsWithProcessor StartsWithProcessor} and
   * {@link EndsWithProcessor EndsWithProcessor}
   * <br> For other types and {@code void}, {@code null} is returned.
   */
  @SuppressWarnings("unchecked")
  @CheckForNull
  public static <T> T getProcessorDefaultValue(ProcessorType processorType, Class<T> type) {
    checkNotNull(type);
    if (type == Boolean.class) {
      return (T) Boolean.FALSE;
    }
    if (type == Integer.class) {
      return (T) Integer.valueOf(0);
    }
    if (type == Long.class) {
      if (processorType == ProcessorType.MAXLENGTH) {
        return (T) Long.valueOf(Long.MAX_VALUE);
      } else {
        return (T) Long.valueOf(0L);
      }
    }
    if (type == String.class) {
      return (T) "";
    }
    if (type == WordClass.class) {
      return (T) WordClass.all;
    }
    return null;
  }

  public static void setOptions(ParseResult parseResult) {

  }

  public static List<String> searchWord(String word,
      List<CmdOptionConfig> matchedCmdOptionConfigs) {


    if (matchedCmdOptionConfigs.isEmpty()) {
      return Collections.singletonList(word);
    }

    final Set<String> matchedElements = new HashSet<>(
        processorTypeMap.get(matchedCmdOptionConfigs.get(0).getProcessorType())
            .search(word));

    for (int i = 1; i < matchedCmdOptionConfigs.size(); i++) {
      matchedElements.retainAll(
          processorTypeMap.get(matchedCmdOptionConfigs.get(i).getProcessorType())
              .search(word));
    }
    return new ArrayList<>(matchedElements);
  }
}
