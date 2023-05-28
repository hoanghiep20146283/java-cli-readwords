package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.exception.ProcessorException;
import com.lumera.wordsearch.service.ProcessorHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AndCombineProcessor implements CombineProcessor {

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
        matchedWords.clear();
        matchedWords.addAll(matchindWords);
      }
      return new ArrayList<>(matchedWords);
    } catch (Exception ex) {
      throw new ProcessorException("Error Processing word: " + word, ex);
    }
  }
}
