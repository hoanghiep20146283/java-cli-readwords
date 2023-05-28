package com.lumera.wordsearch.exception;

public class ProcessorException extends RuntimeException {

  private static final long serialVersionUID = -8612997579528717120L;

  public ProcessorException(String msg, Throwable throwable) {
    super(msg, throwable);
  }

}
