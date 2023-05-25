package com.lumera.wordsearch.processor;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ContainsOnlyProcessorTest extends TestCase {

  private ContainsOnlyProcessor processor;

  @BeforeEach
  public void setup() {
    // Initialize the ContainsOnlyProcessor object for each test case
    processor = new ContainsOnlyProcessor();
  }

  @Test
  public void testSearch_WordContainsOnlyOptionChars_ReturnsListWithWord() {
    // Prepare the data
    processor.setOptionValue("abc");

    // Call the method under test
    final List<String> result = processor.search("abc");

    // Check the result
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("abc", result.get(0));
  }

  @Test
  public void testSearch_WordContainsNotOnlyOptionChars_ReturnsListWithWord() {
    // Prepare the data
    processor.setOptionValue("abc");

    // Call the method under test
    final List<String> result = processor.search("abcdef");

    // Check the result
    Assertions.assertEquals(0, result.size());
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void testSearch_WordContainsOnlyOptionCharsAndRepeated_ReturnsEmptyList() {
    // Prepare the data
    processor.setOptionValue("abc");

    // Call the method under test
    final List<String> result = processor.search("abcabc");

    // Check the result
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("abcabc", result.get(0));
  }

  @Test
  public void testSearch_NullWord_ReturnsEmptyList() {
    // Prepare the data
    processor.setOptionValue("abc");

    // Call the method under test
    final List<String> result = processor.search(null);

    // Check the result
    Assertions.assertEquals(0, result.size());
    Assertions.assertTrue(result.isEmpty());
  }
}