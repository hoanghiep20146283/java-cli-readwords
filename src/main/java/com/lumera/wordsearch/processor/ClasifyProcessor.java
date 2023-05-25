package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.constant.WordClass;
import com.lumera.wordsearch.constant.WordClassOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public class ClasifyProcessor extends Processor<WordClassOptions> {

  private final Set<String> filteredWord = new HashSet<>();

  /**
   * Check if the word belongs to one of the word classifications listed in the input parameter <br>
   * So combine the classifications using: {@link  java.util.stream.Stream#anyMatch(Predicate)}
   *
   * @param word the word read from input file.
   * @return {@code true} if the word belongs to one of the word classifications listed in the input
   * parameter, otherwise {@code false}
   */
  @Override
  public List<String> search(String word) {
    HashSet<String> matchedWords = new HashSet<>();
    optionValue.getWordClasss()
        .forEach(processWordClass(word, matchedWords));
    return new ArrayList<>(matchedWords);
  }

  @NotNull
  private Consumer<WordClass> processWordClass(String word, HashSet<String> matchedWords) {
    return wordClass -> {
      switch (wordClass) {
        case isogram: {
          if (hasDuplicateCharacters(word)) {
            matchedWords.add(word);
          }
          break;
        }
        case palindrome: {
          if (isPalindrome(word)) {
            matchedWords.add(word);
          }
          break;
        }
        case semordnilap: {
          processSemordnilapWord(word, matchedWords);
          break;
        }
        default:
          matchedWords.add(word);
      }
    };
  }

  private void processSemordnilapWord(String word, HashSet<String> matchedWords) {
    String reversedWord = new StringBuilder(word).reverse().toString();
    if (filteredWord.contains(reversedWord)) {
      filteredWord.add(word);
      matchedWords.add(word);
      matchedWords.add(reversedWord);
    } else {
      filteredWord.add(word);
    }
    return;
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
   *             <p>
   *             Time complexity: traverse the input string once to count the number of occurrences
   *             of each character. Therefore, traversing the entire string takes O(N) time. Then
   *             iterate through the count array (size 256) to check if there are any repeating
   *             characters. Iterating over the count array has a complexity of O(1) because the
   *             array size is fixed (256 elements). So the total time complexity is O(N).
   *             </p>
   *             <p>
   *             Space complexity: use a fixed array (size 256) to store the number of occurrences
   *             of each character in the string. The count array size is fixed (independent of
   *             string length). So the space complexity is O(1).
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
   * @param word the word read from input file. <br> The solution below has a time complexity of
   *             O(N/2) where N is the length of the word
   * @return {@code true} if word read backwards is the same, otherwise {@code false}
   */
  public boolean isPalindrome(String word) {
    String reversedWord = new StringBuilder(word).reverse().toString();
    return word.equals(reversedWord);
  }
}
