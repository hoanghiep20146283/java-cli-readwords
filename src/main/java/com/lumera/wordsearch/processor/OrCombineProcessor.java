package com.lumera.wordsearch.processor;

import java.util.Set;

public class OrCombineProcessor extends CombineProcessor {


  @Override
  void combineMatchedWords(Set<String> matchedWords, Set<String> matchindWords) {
    matchedWords.addAll(matchindWords);
  }
}
