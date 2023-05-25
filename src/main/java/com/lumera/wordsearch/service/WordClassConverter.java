package com.lumera.wordsearch.service;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class WordClassConverter implements ITypeConverter<WordClassOptions> {

  @Override
  public WordClassOptions convert(String classArgument) {
    if (!classArgument.startsWith("{") && !classArgument.endsWith("}")) {
      return new WordClassOptions(Collections.singletonList(WordClass.valueOf(classArgument)));
    } else if (classArgument.startsWith("{") && classArgument.endsWith("}")) {
      return new WordClassOptions(
          Arrays.stream(classArgument.substring(1, classArgument.length() - 2)
                  .split("\\|"))
              .map(WordClass::valueOf)
              .collect(Collectors.toList()));
    } else {
      throw new TypeConversionException(
          "Invalid format: must be '{class1|class2|...|classn}' or classn but was '" + classArgument
              + "'");
    }
  }
}