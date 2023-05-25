package com.lumera.wordsearch.processor;

import java.util.List;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MaxLengthProcessorTest extends TestCase {

  private MaxLengthProcessor processor;

  @BeforeEach
  public void setup() {
    // Initialize the MaxLengthProcessor object for each test case
    processor = new MaxLengthProcessor();
  }

  @Test
  public void testSearch_WordWithLengthLessThanOrEqualOptionValue_ReturnsListWithWord() {
    // Prepare optional arguments
    processor.setOptionValue(5L);

    // Call the method under test
    final List<String> result = processor.search("hi");

    // Check the result
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("hi", result.get(0));
  }

  @Test
  public void testSearch_WordWithLengthGreaterThanOptionValue_ReturnsEmptyList() {
    // Prepare optional arguments
    processor.setOptionValue(5L);

    // Call the method under test
    final List<String> result = processor.search("hello world");

    // Check the result
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void testSearch_NullWord_ReturnsEmptyList() {
    // Prepare optional arguments
    processor.setOptionValue(10L);

    // Call the method under test
    final List<String> result = processor.search(null);

    // Check the result
    Assertions.assertTrue(result.isEmpty());
  }
}