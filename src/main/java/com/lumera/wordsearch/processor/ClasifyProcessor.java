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
   * @param word the word read from input file. startrn {@code true} if the word belongs to one of
   *             the word classifications listed in the input parameter, otherwise {@code false}
   */
  @Override
  public List<String> search(String word) {
    final HashSet<String> matchedWords = new HashSet<>();
    optionValue.getWordClasss()
        .forEach(processWordClass(word, matchedWords));
    return new ArrayList<>(matchedWords);
  }

  /**
   * Gets a string and current matches to check if the current word being searched is eligible to
   * belong to the matching words
   *
   * @param word         the word input from wordlist.txt
   * @param matchedWords Current matching words
   * @return {@link Consumer<WordClass> } WordClass <br> Based on the wordClass value (taken from
   * the internal state of the processor) to execute the corresponding logic
   */
  @NotNull
  private Consumer<WordClass> processWordClass(String word, HashSet<String> matchedWords) {
    return wordClass -> {
      switch (wordClass) {
        case isogram: {
          if (!hasDuplicateCharacters(word)) {
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

  /**
   * Check if the word is spelled backwards from another word in the list
   *
   * @param word         the word read from input file.
   * @param matchedWords words were matched from the input file.
   *                     <p> Solution: <br>
   *                     Creates an empty HashSet to store the matched words. Iterate over each
   *                     input word. Invert the current word and check if this reversed word exists
   *                     in the HashSet. If exists, then the current element and its inverse are a
   *                     matched pair. Insert these two elements into {@code matchedWords}
   *                     </p>
   *                     <p> If it doesn't exist, add the current element to the
   *                     {@code filteredWord} state. Then, continue traversing the next element.
   *                     Returns a list of matching element pairs.
   *                     </p>
   *                     <p>
   *                     The time complexity is O(n) because we only traverse each element once and
   *                     perform constant operations on each element.
   *                     </p>
   *                     <p>
   *                     The space complexity is O(n) because the solution uses a HashSet to store
   *                     the traversed elements and a matching words to store matching pairs of
   *                     elements.
   *                     </p>
   *                     <p> The worst case occurs when no elements are reversed, and in
   *                     that case both the HashSet and the matching words need to store all the
   *                     elements in the input words.
   *                     </p>
   */
  private void processSemordnilapWord(String word, HashSet<String> matchedWords) {
    final String reversedWord = new StringBuilder(word).reverse().toString();
    if (filteredWord.contains(reversedWord)) {
      filteredWord.add(word);
      matchedWords.add(word);
      matchedWords.add(reversedWord);
    } else {
      filteredWord.add(word);
    }
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
    final int[] charCount = new int[128]; //

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
    final String reversedWord = new StringBuilder(word).reverse().toString();
    return word.equals(reversedWord);
  }

  @Override
  public void reset() {
    filteredWord.clear();
  }
}
