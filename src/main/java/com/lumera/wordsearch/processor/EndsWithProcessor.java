package com.lumera.wordsearch.processor;

public class EndsWithProcessor extends Processor<String> {

  @Override
  public boolean search(String word) {
    return word != null && word.endsWith(optionValue);
  }
}
