package com.lumera.wordsearch.processor;

public class EndsWithProcessor implements Processor<String> {

  @Override
  public boolean search(String prefix, String word) {
    return word != null && word.endsWith(prefix);
  }
}
