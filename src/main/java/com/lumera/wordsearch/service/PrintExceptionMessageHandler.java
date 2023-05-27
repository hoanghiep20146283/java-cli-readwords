package com.lumera.wordsearch.service;

import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

/**
 * Runtime exception handling can be customized by overriding
 * {@link  IExecutionExceptionHandler#handleExecutionException(Exception, CommandLine,
 * ParseResult)}
 * <br>
 * <p>
 * If the business logic of the command throws an exception, the execute method prints the stack
 * trace of the exception and returns an exit code. This can be customized by configuring an
 * {@link  IExecutionExceptionHandler},
 * </p>
 */
public class PrintExceptionMessageHandler implements IExecutionExceptionHandler {

  /**
   * Bold red error message <br>
   * Print to the console error messages, not the entire stack trace
   */
  @Override
  public int handleExecutionException(Exception ex,
      CommandLine cmd,
      ParseResult parseResult) {


    cmd.getErr().println(cmd.getColorScheme().errorText(ex.getMessage()));

    return cmd.getExitCodeExceptionMapper() != null
        ? cmd.getExitCodeExceptionMapper().getExitCode(ex)
        : cmd.getCommandSpec().exitCodeOnExecutionException();
  }
}