package com.lumera.wordsearch.constant;

public enum ExitCode {
  SUCCESS(0),
  FAILURE(-1);

  private final int exitCode;

  ExitCode(int returnCode) {
    exitCode = returnCode;
  }

  public int getExitCode() {
    return exitCode;
  }
}