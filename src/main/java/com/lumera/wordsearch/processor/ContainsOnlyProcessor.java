package com.lumera.wordsearch.processor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContainsOnlyProcessor extends Processor<String> {

  private final Set<Character> inputChars = new HashSet<>();

  @Override
  public void setOptionValue(String optionValue) {
    this.optionValue = optionValue;
    for (char c : optionValue.toCharArray()) {
      inputChars.add(c);
    }
  }

  @Override
  public List<String> search(String word) {
    if (word == null || word.isEmpty()) {
      return Collections.emptyList();
    }

    boolean isMatch = true;
    for (char c : word.toCharArray()) {
      if (!inputChars.contains(c)) {
        isMatch = false;
        break;
      }
    }
    return isMatch ? Collections.singletonList(word) : Collections.emptyList();
  }
}
