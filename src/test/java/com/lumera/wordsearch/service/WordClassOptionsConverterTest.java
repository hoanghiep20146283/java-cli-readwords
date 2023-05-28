package com.lumera.wordsearch.service;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordClassOptionsConverterTest {

  private final WordClassOptionsConverter converter = new WordClassOptionsConverter();

  @Test
  void convert_allClassArgument_ReturnsWordClassOptionsWithSingleClass() {
    final WordClassOptions wordClassOptions = converter.convert("all");

    // Assert
    Assertions.assertEquals(1, wordClassOptions.getWordClasss().size());
    Assertions.assertEquals(WordClass.all, wordClassOptions.getWordClasss().get(0));
  }

  @Test
  void convert_MultipleClassArgument_ReturnsWordClassOptionsWithMultipleClasses() {
    final WordClassOptions wordClassOptions = converter.convert(
        "{  isogram|palindrome| semordnilap| all}");

    // Assert
    Assertions.assertEquals(4, wordClassOptions.getWordClasss().size());
    Assertions.assertEquals(WordClass.isogram, wordClassOptions.getWordClasss().get(0));
    Assertions.assertEquals(WordClass.palindrome, wordClassOptions.getWordClasss().get(1));
    Assertions.assertEquals(WordClass.semordnilap, wordClassOptions.getWordClasss().get(2));
    Assertions.assertEquals(WordClass.all, wordClassOptions.getWordClasss().get(3));
  }

  @Test
  void convert_InvalidClassArgument_ThrowsTypeConversionException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> converter.convert("UNKNOWN"));
  }
}