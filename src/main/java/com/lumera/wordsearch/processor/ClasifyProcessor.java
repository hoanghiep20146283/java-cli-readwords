package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.ArrayList;
import java.util.List;

public class ClasifyProcessor extends Processor<WordClassOptions> {

  private final List<String> result = new ArrayList<>();

  @Override
  public boolean search(String word) {
    return optionValue.getWordClasss().stream().allMatch(wordClass -> {
      switch (wordClass) {
        case isogram: {
          return hasDuplicateCharacters(word);
        }
        default:
          return true;
      }
    });
  }


  /**
   * Check if word contains duplicated characters
   *
   * @param word the word read from input file. <br> Assume the input word contains only
   *              ASCIIcharacters from 0 to 127
   * @return {@code true} if either word does not contain any duplicated characters, otherwise
   * {@code false}
   */
  public boolean hasDuplicateCharacters(String word) {
    int[] charCount = new int[128]; //

    for (char c : word.toCharArray()) {
      if (++charCount[c] > 1) {
        return true;
      }
    }

    return false;
  }
}
