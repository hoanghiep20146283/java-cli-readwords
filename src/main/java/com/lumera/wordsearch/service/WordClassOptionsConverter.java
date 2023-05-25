package com.lumera.wordsearch.service;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class WordClassOptionsConverter implements ITypeConverter<WordClassOptions> {

  /**
   * Validate and convert a string to an object containing a list of WordClasses
   *
   * @param classArgument Read from command line - represents the list containing the classes of
   *                      word The list of classes listed in
   *                      {@link  WordClass}
   * @return {@code true} if the word belongs to one of the classes listed in the list passed in the
   * {@code classArgument}, otherwise {@code false}
   * @throws TypeConversionException if {@code classArgument} format is invalid
   *                                 <p> When a
   *                                 TypeConversionException is thrown, picocli will automatically handle and show an error
   *                                 message that indicates the problematic option, followed by the
   *                                 exception message text
   *                                 </p>
   */
  @Override
  public WordClassOptions convert(String classArgument) {
    if (!classArgument.startsWith("{") && !classArgument.endsWith("}")) {
      return new WordClassOptions(Collections.singletonList(WordClass.valueOf(classArgument)));
    } else if (classArgument.startsWith("{") && classArgument.endsWith("}")) {
      return new WordClassOptions(
          Arrays.stream(classArgument.substring(1, classArgument.length() - 1)
                  .split("\\|"))
              .map(WordClass::valueOf)
              .collect(Collectors.toList()));
    } else {
      throw new TypeConversionException(
          "Invalid format: must be '{class1|class2|...|classn}' or classn but was '" + classArgument
              + '\'');
    }
  }
}