package com.lumera.wordsearch.processor;

import com.lumera.wordsearch.constant.WordClass;
import java.util.ArrayList;
import java.util.List;

public class ClasifyProcessor extends Processor<WordClass> {

  private final List<String> result = new ArrayList<>();

  @Override
  public boolean search(String word) {
    switch (optionValue) {
      case all:
      default:
        return true;
    }
  }
}
