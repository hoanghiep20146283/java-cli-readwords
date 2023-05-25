package com.lumera.wordsearch.processor;

public abstract class Processor<T> {

  protected T optionValue;

  public void setOptionValue(T optionValue) {
    this.optionValue = optionValue;
  }

  abstract public boolean search(String word);
}
