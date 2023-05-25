package com.lumera.wordsearch.processor;

import java.util.HashSet;
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
  public boolean search(String word) {
    boolean isMatch = true;
    for (char c : word.toCharArray()) {
      if (!inputChars.contains(c)) {
        isMatch = false;
        break;
      }
    }
    return isMatch;
  }
}
