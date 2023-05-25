package com.lumera.wordsearch.processor;

import java.util.Collections;
import java.util.List;

public class StartsWithProcessor extends Processor<String> {

  @Override
  public List<String> search(String word) {
    return word != null && word.startsWith(optionValue) ? Collections.singletonList(word)
        : Collections.emptyList();
  }
}
