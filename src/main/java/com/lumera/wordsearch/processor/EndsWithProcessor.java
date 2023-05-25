package com.lumera.wordsearch.processor;

import java.util.Collections;
import java.util.List;

public class EndsWithProcessor extends Processor<String> {

  @Override
  public List<String> search(String word) {
    return word != null && word.endsWith(optionValue) ? Collections.singletonList(word)
        : Collections.emptyList();
  }
}
