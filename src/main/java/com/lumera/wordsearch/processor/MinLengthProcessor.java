package com.lumera.wordsearch.processor;

public class MinLengthProcessor implements Processor<Long> {

  @Override
  public boolean search(Long maxLength, String word) {
    return word != null && word.length() >= maxLength;
  }
}
