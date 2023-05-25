package com.lumera.wordsearch.processor;

import java.util.Collections;
import java.util.List;

public class MaxLengthProcessor extends Processor<Long> {

  @Override
  public List<String> search(String word) {
    return word != null && word.length() <= optionValue ? Collections.singletonList(word)
        : Collections.emptyList();
  }
}
