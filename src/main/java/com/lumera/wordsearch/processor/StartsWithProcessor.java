package com.lumera.wordsearch.processor;

public class StartsWithProcessor extends Processor<String> {

  @Override
  public boolean search(String word) {
    return word != null && word.startsWith(optionValue);
  }
}
