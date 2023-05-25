package com.lumera.wordsearch.processor;

import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class MinLengthProcessor extends Processor<Long> {

  @Override
  public List<String> search(@Nullable String word) {
    return word != null && word.length() >= optionValue ? Collections.singletonList(word)
        : Collections.emptyList();
  }
}
