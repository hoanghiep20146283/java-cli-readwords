package com.lumera.wordsearch.processor;

public class StartsWithProcessor implements Processor<String> {

  @Override
  public boolean search(String prefix, String word) {
    return word != null && word.startsWith(prefix);
  }
}
