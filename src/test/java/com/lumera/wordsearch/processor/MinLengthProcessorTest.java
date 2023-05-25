package com.lumera.wordsearch.processor;

import java.util.List;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinLengthProcessorTest extends TestCase {

  private MinLengthProcessor processor;

  @BeforeEach
  public void setup() {
    // Initialize the MinLengthProcessor object for each test case
    processor = new MinLengthProcessor();
  }

  @Test
  public void testSearch_WordWithLengthEqualOptionValue_ReturnsListWithWord() {
    // Prepare optional arguments
    processor.setOptionValue(10L);

    // Call the method under test
    final List<String> result = processor.search("Hello Anna");

    // Verify the result
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("Hello Anna", result.get(0));
  }

  @Test
  public void testSearch_WordWithLengthLessThanOptionValue_ReturnsEmptyList() {
    // Prepare optional arguments
    processor.setOptionValue(10L);

    // Call the method under test
    final List<String> result = processor.search("Lumera");

    // Verify the result
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void testSearch_NullWord_ReturnsEmptyList() {
    processor.setOptionValue(5L);

    // Call the method under test
    final List<String> result = processor.search(null);

    // Verify the result
    Assertions.assertTrue(result.isEmpty());
  }
}