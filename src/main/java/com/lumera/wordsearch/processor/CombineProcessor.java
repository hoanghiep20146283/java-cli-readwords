package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import java.util.List;

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

@FunctionalInterface
public interface CombineProcessor {

  List<String> combine(String word, List<CmdOptionConfig> matchedCmdOptionConfigs);
}
