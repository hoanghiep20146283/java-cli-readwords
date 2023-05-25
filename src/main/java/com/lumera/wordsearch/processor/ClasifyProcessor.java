package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.ArrayList;
import java.util.List;

public class ClasifyProcessor extends Processor<WordClassOptions> {

  private final List<String> result = new ArrayList<>();

  /**
   * Check if the word belongs to one of the word classifications listed in the input parameter
   *
   * @param word the word read from input file.
   * @return {@code true} if the word belongs to one of the word classifications listed in the input
   * parameter, otherwise {@code false}
   */
  @Override
  public boolean search(String word) {
    return optionValue.getWordClasss()
        .stream()
        .anyMatch(wordClass -> {
          switch (wordClass) {
            case isogram: {
              return hasDuplicateCharacters(word);
            }
            case palindrome: {
              return isPalindrome(word);
            }
            case semordnilap: {

            }
            default:
              return true;
          }
        });
  }


  /**
   * Check if word contains duplicated characters
   *
   * @param word the word read from input file.
   *             <p> Assume the input word contains only
   *             ASCIIcharacters from 0 to 127
   *             </p>
   *             <p> Solution: This method use a charCount array of size
   *             128 (corresponding to ASCII characters from 0 to 127). Initially, all elements in
   *             the array are initialized to a default value of 0. <br> Then iterate through each
   *             character in the input string and increment the corresponding value in the
   *             charCount array. If the value after increment becomes 2 or more, which means there
   *             is a repeated character, we return true and we're done. If there are no repeated
   *             characters, we return false after traversing the entire string
   *             </p>
   * @return {@code true} if word does not contain any duplicated characters, otherwise
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

  /**
   * Check if the word read backwards is the same
   *
   * @param word the word read from input file.
   * The solution below has a time complexity of O(N/2) where N is the length of the word
   * @return {@code true} if word read backwards is the same, otherwise {@code false}
   */
  public boolean isPalindrome(String word) {
    String reversedWord = new StringBuilder(word).reverse().toString();
    return word.equals(reversedWord);
  }
}
