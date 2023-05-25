package com.lumera.wordsearch.processor;

public class MaxLengthProcessor extends Processor<Long> {

  @Override
  public boolean search(String word) {
    return word != null && word.length() <= optionValue;
  }
}
