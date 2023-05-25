package com.lumera.wordsearch.processor;

public class MinLengthProcessor extends Processor<Long> {

  public boolean search(String word) {
    return word != null && word.length() >= optionValue;
  }
}
