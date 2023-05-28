package com.lumera.wordsearch.processor;

import java.util.HashSet;
import java.util.Set;

public class AndCombineProcessor extends CombineProcessor {

  @Override
  void combineMatchedWords(Set<String> matchedWords, Set<String> matchindWords) {
    matchedWords.clear();
    matchedWords.addAll(matchindWords);
  }
}
