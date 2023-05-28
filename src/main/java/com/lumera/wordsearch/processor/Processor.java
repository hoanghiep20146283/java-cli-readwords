package com.lumera.wordsearch.processor;

import java.util.List;
import picocli.CommandLine.ParseResult;

/**
 * This abstract class is the superclass of all processors has the role of searching for keywords
 * that satisfy a certain condition (arguments input from cli, words input from wordlist.txt file)
 *
 * @param <T> the type of optionValue used for processor's logic
 * @author James Hoang
 * @see ContainsOnlyProcessor
 * @see ClasifyProcessor
 * @see EndsWithProcessor
 * @see StartsWithProcessor
 * @see MaxLengthProcessor
 * @see MinLengthProcessor
 */
public abstract class Processor<T> {

  /**
   * The option value is dynamically parsed from the CLI. <br> Wil be set in
   * {@link com.lumera.wordsearch.service.ProcessorHelper#setOptions(ParseResult)}
   */
  protected T optionValue;

  /**
   * Set conditions for keywords to be processed by processor
   *
   * @param optionValue the argument input from CLI
   */
  public void setOptionValue(T optionValue) {
    this.optionValue = optionValue;
  }

  /**
   * Takes a string and returns strings that satisfy the processor's condition
   * @param word the word input from wordlist.txt
   * @return The list of matching words (also includes the word that is parsed and stored in the
   * processor's internal state - affected by the word being searched)
   */
  public abstract List<String> search(String word);

  /**
   * Resets the {@code state} of this {@code processor} to initial, so that all cumulative output is
   * present in processor's memory discarded. The state can then be reused, reuse and make the word
   * search process idempotent.
   *
   * @see ClasifyProcessor#reset()
   * @see ContainsOnlyProcessor#reset()
   */
  public void reset() {
  }
}
