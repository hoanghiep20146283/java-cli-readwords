package com.lumera.wordsearch.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StartsWithProcessorTest {

  private StartsWithProcessor processor;

  @BeforeEach
  public void setup() {
    // Initialize the StartsWithProcessor object for each test case
    processor = new StartsWithProcessor();
  }

  @Test
  public void testSearch_WordStartsWithOptionValue_ReturnsListWithWord() {
    // Prepare optional arguments
    processor.setOptionValue("Ja");


    // Call the method under test
    final List<String> result = processor.search("James");

    // Check the result
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("James", result.get(0));
  }

  @Test
  public void testSearch_WordDoesNotStartWithOptionValue_ReturnsEmptyList() {
    // Prepare option
    processor.setOptionValue("James");

    // Call the method under test
    final List<String> result = processor.search("word");

    // Check the result
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void testSearch_NullWord_ReturnsEmptyList() {
    // Prepare optional arguments
    processor.setOptionValue("test");

    // Call the method under test
    final List<String> result = processor.search(null);

    // Check the result
    Assertions.assertTrue(result.isEmpty());
  }
}