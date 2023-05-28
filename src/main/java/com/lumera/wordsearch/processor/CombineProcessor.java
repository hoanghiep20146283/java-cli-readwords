package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import java.util.List;

/**
 * This abstract class is the superclass of all associative processors whose role is to combine
 * output from multiple processors. <br> You can choose to implement CombineProcessor (currently
 * support andCombineProcessor and orCombineProcessor with and/or logic respectively) in file
 * <a href="file:../resources/config.yml">resources/config.yml</a> <br>
 * Property: {@code combineProcessorType}
 *
 * @author James Hoang
 * @see AndCombineProcessor
 * @see OrCombineProcessor
 */

@FunctionalInterface
public interface CombineProcessor {

  List<String> combine(String word, List<CmdOptionConfig> matchedCmdOptionConfigs);

}
