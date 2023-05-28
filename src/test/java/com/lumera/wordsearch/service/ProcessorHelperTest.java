package com.lumera.wordsearch.service;

import com.lumera.wordsearch.WordSearchApplication;
import com.lumera.wordsearch.config.XmlConfig;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ProcessorType;
import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import com.lumera.wordsearch.processor.Processor;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import picocli.CommandLine.ParseResult;
import java.util.Collections;
import java.util.List;

public class ProcessorHelperTest {

  private ParseResult parseResult;

  private XmlConfig xmlConfig;

  private CmdOptionConfig classCmdOptionConfig;
  private CmdOptionConfig minLengthCmdOptionConfig;

  @BeforeEach
  public void setUp() {
    parseResult = Mockito.mock(ParseResult.class);
    xmlConfig = Mockito.mock(XmlConfig.class);
    classCmdOptionConfig = Mockito.mock(CmdOptionConfig.class);
    minLengthCmdOptionConfig = Mockito.mock(CmdOptionConfig.class);
  }

  @AfterEach
  public void tearDown() {
    ProcessorHelper.processorTypeMap.values().forEach(Processor::reset);
  }

  @Test
  public void getProcessorDefaultValue_WithBooleanType_ReturnsFalse() {
    final ProcessorType processorType = ProcessorType.CLASS;
    final Class<Boolean> type = Boolean.class;

    final Boolean result = ProcessorHelper.getProcessorDefaultValue(processorType, type);

    // The expected behavior is that when the type is Boolean, the method should return FALSE.
    Assertions.assertEquals(Boolean.FALSE, result);
  }

  @Test
  public void getProcessorDefaultValue_WithIntegerType_ReturnsZero() {
    final ProcessorType processorType = ProcessorType.MINLENGTH;
    final Class<Integer>
        type = Integer.class;

    final Integer result = ProcessorHelper.getProcessorDefaultValue(processorType, type);

    // The expected behavior is that when the type is Integer, the method should return 0.
    Assertions.assertEquals(Integer.valueOf(0), result);
  }

  @Test
  public void getProcessorDefaultValue_WithLongTypeAndMaxLengthProcessor_ReturnsMaxLongValue() {
    final ProcessorType processorType = ProcessorType.MAXLENGTH;
    final Class<Long> type = Long.class;

    final Long result = ProcessorHelper.getProcessorDefaultValue(processorType, type);

    // The expected behavior is that when the type is Long and the processor type is MaxLengthProcessor,
    // the method should return Long.MAX_VALUE.
    Assertions.assertEquals(Long.valueOf(Long.MAX_VALUE), result);
  }

  @Test
  public void getProcessorDefaultValue_WithLongTypeAndOtherProcessor_ReturnsZero() {
    final ProcessorType processorType = ProcessorType.MINLENGTH;
    final Class<Long> type = Long.class;

    final Long result = ProcessorHelper.getProcessorDefaultValue(processorType, type);

    // The expected behavior is that when the type is Long and the processor type is not MaxLengthProcessor,
    // the method should return 0L.
    Assertions.assertEquals(Long.valueOf(0L), result);
  }

  @Test
  public void getProcessorDefaultValue_WithStringType_ReturnsEmptyString() {
    final ProcessorType processorType = ProcessorType.STARTSWITH;
    final Class<String> type = String.class;

    final String result = ProcessorHelper.getProcessorDefaultValue(processorType, type);

    // The expected behavior is that when the type is String, the method should return an empty string.
    Assertions.assertEquals("", result);
  }

  @Test
  public void getProcessorDefaultValue_WithWordClassType_ReturnsAllWordClass() {
    final ProcessorType processorType = ProcessorType.CLASS;
    final Class<WordClass> type = WordClass.class;

    final WordClass result = ProcessorHelper.getProcessorDefaultValue(processorType, type);

    // The expected behavior is that when the type is WordClass, the method should return WordClass.all.
    Assertions.assertEquals(WordClass.all, result);
  }

  @Test
  public void search_byEndsWithProcess_ReturnSingletonList() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.ENDSWITH);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(parseResult.matchedOptionValue(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()))
        .thenReturn("endsWithArgument");

    ProcessorHelper.setOptions(parseResult);

    final List<String> matchedWords = ProcessorHelper.processorTypeMap.get(ProcessorType.ENDSWITH)
        .search("wordendsWithArgument");
    Assertions.assertEquals(1, matchedWords.size());
    Assertions.assertEquals("wordendsWithArgument", matchedWords.get(0));
  }

  @Test
  public void search_byStartsWithProcess_ReturnSingletonList() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.STARTSWITH);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .thenReturn("start");

    ProcessorHelper.setOptions(parseResult);

    final List<String> matchedWords = ProcessorHelper.processorTypeMap.get(ProcessorType.STARTSWITH)
        .search("startsWith");
    Assertions.assertEquals(1, matchedWords.size());
    Assertions.assertEquals("startsWith", matchedWords.get(0));
  }

  @Test
  public void search_byContainsOnlyProcess_ReturnSingletonList() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.CONTAINSONLY);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .thenReturn("abc");

    ProcessorHelper.setOptions(parseResult);

    final List<String> matchedWords = ProcessorHelper.processorTypeMap.get(
            ProcessorType.CONTAINSONLY)
        .search("abcabc");
    Assertions.assertEquals(1, matchedWords.size());
    Assertions.assertEquals("abcabc", matchedWords.get(0));
  }

  @Test
  public void setOption_bySemordnilapClassProcess_resultSingletonList() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.CLASS);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(new WordClassOptions(Collections.singletonList(WordClass.semordnilap)));

    ProcessorHelper.setOptions(parseResult);

    final List<String> firstResult = ProcessorHelper.processorTypeMap.get(ProcessorType.CLASS)
        .search("test");
    Assertions.assertTrue(firstResult.isEmpty());

    final List<String> result = ProcessorHelper.processorTypeMap.get(ProcessorType.CLASS)
        .search("tset");
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("test", result.get(0));
    Assertions.assertEquals("tset", result.get(1));
  }

  @Test
  public void setOption_bySemordnilapClassProcess_resultListWith2Elements() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.CLASS);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(new WordClassOptions(Collections.singletonList(WordClass.semordnilap)));

    ProcessorHelper.setOptions(parseResult);

    ProcessorHelper.processorTypeMap.get(ProcessorType.CLASS).search("test");

    final List<String> result = ProcessorHelper.processorTypeMap.get(ProcessorType.CLASS)
        .search("tset");
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("test", result.get(0));
    Assertions.assertEquals("tset", result.get(1));
  }

  @Test
  public void searchWords_byEmptyProcess_resultEmptyList() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.CLASS);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(new WordClassOptions(Collections.singletonList(WordClass.semordnilap)));

    ProcessorHelper.setOptions(parseResult);

    ProcessorHelper.searchWord("test", Collections.emptyList());

    final List<String> result = ProcessorHelper.searchWord("tset",
        Collections.singletonList(classCmdOptionConfig));
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void searchWords_bySemordnilapClassProcess_resultListWith2Elements() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.CLASS);
    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Collections.singletonList(classCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(new WordClassOptions(Collections.singletonList(WordClass.semordnilap)));

    ProcessorHelper.setOptions(parseResult);

    ProcessorHelper.searchWord("test", Collections.singletonList(classCmdOptionConfig));

    final List<String> result = ProcessorHelper.searchWord("tset",
        Collections.singletonList(classCmdOptionConfig));
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("test", result.get(0));
    Assertions.assertEquals("tset", result.get(1));
  }

  @Test
  public void searchWords_bySemordnilapClassProcessAndMinLengthProcessor_resultListWith2Elements() {
    Mockito.when(classCmdOptionConfig.getName()).thenReturn("test class option");
    Mockito.when(classCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.CLASS);

    Mockito.when(minLengthCmdOptionConfig.getName()).thenReturn("test minlength option");
    Mockito.when(minLengthCmdOptionConfig.getProcessorType()).thenReturn(ProcessorType.MINLENGTH);

    Mockito.when(xmlConfig.getCmdOptionConfigs())
        .thenReturn(Arrays.asList(classCmdOptionConfig, minLengthCmdOptionConfig));
    WordSearchApplication.xmlConfig = xmlConfig;

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(WordClass.class)))
        .thenReturn(WordClass.semordnilap);

    Mockito.when(
            parseResult.matchedOptionValue(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong()))
        .thenReturn(5L);

    ProcessorHelper.setOptions(parseResult);

    final List<CmdOptionConfig> cmdOptionConfigs = Arrays.asList(classCmdOptionConfig,
        minLengthCmdOptionConfig);

    ProcessorHelper.searchWord("test", cmdOptionConfigs);
    ProcessorHelper.searchWord("abcdef", cmdOptionConfigs);
    ProcessorHelper.searchWord("fedcba", cmdOptionConfigs);

    final List<String> result = ProcessorHelper.searchWord("tset", cmdOptionConfigs);
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("test", result.get(0));
    Assertions.assertEquals("tset", result.get(1));
  }

}

