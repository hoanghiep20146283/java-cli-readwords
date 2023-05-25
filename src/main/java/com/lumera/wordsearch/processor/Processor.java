package com.lumera.wordsearch.processor;

import java.util.List;

/**
 * This abstract class is the superclass of all processors has the role of searching for keywords
 * that satisfy a certain condition (arguments input from cli, words input from wordlist.txt file)
 *
 * @author James Hoang
 * @see ContainsOnlyProcessor
 * @see ClasifyProcessor
 * @see EndsWithProcessor
 * @see StartsWithProcessor
 * @see MaxLengthProcessor
 * @see MinLengthProcessor
 */
public abstract class Processor<T> {

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
   *
   * @param word the word input from wordlist.txt
   */
  public abstract List<String> search(String word);
}
