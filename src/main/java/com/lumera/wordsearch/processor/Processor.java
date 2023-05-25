package com.lumera.wordsearch.processor;

import java.util.List;

public abstract class Processor<T> {

  protected T optionValue;

  public void setOptionValue(T optionValue) {
    this.optionValue = optionValue;
  }

  abstract public List<String> search(String word);
}
