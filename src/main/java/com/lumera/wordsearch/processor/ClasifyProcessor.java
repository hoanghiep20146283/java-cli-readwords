package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class ClasifyProcessor {


  public List<String> search(List<String> allWords) {
    final HashSet<String> filteredWords = new HashSet<>();
    final HashSet<String> matchedWords = new HashSet<>();
    for (int i = 0; i < allWords.size(); i++) {
      String matchindWord = allWords.get(i);
      if (filteredWords.contains(new StringBuilder(matchindWord).reverse().toString())) {
        matchedWords.add(matchindWord);
        matchedWords.add(new StringBuilder(matchindWord).reverse().toString());
      }
      filteredWords.add(matchindWord);
    }
    return matchedWords.stream().collect(Collectors.toList());
  }
}
