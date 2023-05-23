package com.lumera.wordsearch.processor;

public interface Processor<T> {

  boolean search(T argument, String word);
}
