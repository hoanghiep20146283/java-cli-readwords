package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.exception.ProcessorException;
import com.lumera.wordsearch.service.ProcessorHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This abstract class is the superclass of all associative processors whose role is to combine
 * output from multiple processors.
 *
 * @author James Hoang
 * @see ContainsOnlyProcessor
 * @see ClasifyProcessor
 * @see EndsWithProcessor
 * @see StartsWithProcessor
 * @see MaxLengthProcessor
 * @see MinLengthProcessor
 */

public abstract class CombineProcessor {

  abstract void combineMatchedWords(Set<String> matchedWords, Set<String> matchindWords);

  public List<String> combine(String word, List<CmdOptionConfig> matchedCmdOptionConfigs) {
    try {
      if (matchedCmdOptionConfigs.isEmpty()) {
        return Collections.singletonList(word);
      }

      final Set<String> matchedWords = new HashSet<>(
          ProcessorHelper.processorTypeMap.get(matchedCmdOptionConfigs.get(0).getProcessorType())
              .search(word));

      for (int i = 1; i < matchedCmdOptionConfigs.size(); i++) {
        final Set<String> matchindWords = new HashSet<>();
        for (String matchedWord : matchedWords) {
          matchindWords.addAll(
              ProcessorHelper.processorTypeMap.get(
                      matchedCmdOptionConfigs.get(i).getProcessorType())
                  .search(matchedWord));
        }
        combineMatchedWords(matchedWords, matchindWords);
      }
      return new ArrayList<>(matchedWords);
    } catch (Exception ex) {
      throw new ProcessorException("Error Processing word: " + word, ex);
    }
  }
}
