package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class ClasifyProcessorTest {

  private ClasifyProcessor processor;

  @BeforeEach
  public void setUp() {
    processor = new ClasifyProcessor();
  }

  /**
   * The expected behavior is that the word "isogram" should match {@link  WordClass#isogram} (no
   * duplicate characters)
   */
  @Test
  public void search_WithIsogramWord_ReturnsSingletonList() {
    processor.setOptionValue(new WordClassOptions(Collections.singletonList(WordClass.isogram)));
    final List<String> result = processor.search("isogram");

    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("isogram", result.get(0));
  }

  /**
   * The expected behavior is that the word should match {@link  WordClass#palindrome} (word that
   * reads the same backwards)
   */
  @Test
  public void search_WithPalindromeWord_ReturnsSingletonList() {
    processor.setOptionValue(new WordClassOptions(Collections.singletonList(WordClass.palindrome)));
    final List<String> result = processor.search("aabbaa");

    // The expected behavior is that the word "isogram" should match WordClass.isogram
    // so the result should be an empty list.
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("aabbaa", result.get(0));
  }

  /**
   * The expected behavior is that the word should match {@link  WordClass#semordnilap} (word that
   * reads the same backwards)
   */
  @Test
  public void search_WithSemordnilapWord_ReturnsListWith2Elements() {
    processor.setOptionValue(
        new WordClassOptions(Collections.singletonList(WordClass.semordnilap)));
    final List<String> firstResult = processor.search("test");
    final List<String> secondResult = processor.search("tset");

    Assertions.assertTrue(firstResult.isEmpty());

    Assertions.assertEquals(2, secondResult.size());
    Assertions.assertEquals("test", secondResult.get(0));
    Assertions.assertEquals("tset", secondResult.get(1));
  }


  /**
   * The expected behavior is that the word should match {@link  WordClass#palindrome} (word that
   * reads the same backwards)
   */
  @Test
  public void search_MixSemordnilapWord_WithPalindromeWord_ReturnsListWithMultipleElements() {
    processor.setOptionValue(
        new WordClassOptions(Arrays.asList(WordClass.semordnilap, WordClass.palindrome)));
    final List<String> firstResult = processor.search("test");
    final List<String> secondResult = processor.search("tset");
    final List<String> lastResult = processor.search("assa");

    Assertions.assertTrue(firstResult.isEmpty());

    Assertions.assertEquals(2, secondResult.size());
    Assertions.assertEquals("test", secondResult.get(0));
    Assertions.assertEquals("tset", secondResult.get(1));

    Assertions.assertEquals(1, lastResult.size());
    Assertions.assertEquals("assa", lastResult.get(0));
  }

  @Test
  public void search_MixAllClass_ReturnsListWithMultipleElements() {
    processor.setOptionValue(
        new WordClassOptions(
            Arrays.asList(WordClass.isogram, WordClass.semordnilap, WordClass.palindrome)));
    final List<String> firstResult = processor.search("test");
    final List<String> secondResult = processor.search("tset");
    final List<String> thirdResult = processor.search("third");
    final List<String> lastResult = processor.search("level");

    Assertions.assertTrue(firstResult.isEmpty());

    Assertions.assertEquals(2, secondResult.size());
    Assertions.assertEquals("test", secondResult.get(0));
    Assertions.assertEquals("tset", secondResult.get(1));

    Assertions.assertEquals(1, thirdResult.size());
    Assertions.assertEquals("third", thirdResult.get(0));

    Assertions.assertEquals(1, lastResult.size());
    Assertions.assertEquals("level", lastResult.get(0));
  }

  @Test
  public void search_MixAllClass_NoneMatch() {
    processor.setOptionValue(
        new WordClassOptions(
            Arrays.asList(WordClass.isogram, WordClass.semordnilap, WordClass.palindrome)));
    final List<String> firstResult = processor.search("tesst");

    Assertions.assertTrue(firstResult.isEmpty());
  }

}