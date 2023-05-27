package com.lumera.wordsearch;

import com.lumera.wordsearch.command.SearchCommand;
import com.lumera.wordsearch.config.XmlConfig;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import com.lumera.wordsearch.constant.ExitCode;
import com.lumera.wordsearch.service.PrintExceptionMessageHandler;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;

/**
 * Entry point of application.
 * <p>
 * Exception handling: If the business logic of the command throws an exception, the execute method
 * prints the stack trace of the exception and returns an exit code. This can be customized by
 * configuring an {@link  IExecutionExceptionHandler},
 * </p>
 * <p>
 * Extensibility: We can configure, add or remove the list of processors (corresponding to the
 * command list) used in the program in the file:
 * <a href="file:../resources/config.yml">resources/config.yml</a>
 * </p>
 */
@Slf4j
public final class WordSearchApplication {

  public static XmlConfig xmlConfig;

  public static void main(String[] args) {
    try (InputStream inputStream = WordSearchApplication.class.getClassLoader()
        .getResourceAsStream("config.yml")) {
      final Yaml yaml = new Yaml(new Constructor(XmlConfig.class));
      xmlConfig = (XmlConfig) yaml.load(inputStream);

      final CommandSpec spec = CommandSpec.create();
      spec.mixinStandardHelpOptions(true);
      spec.name("search");

      for (CmdOptionConfig cmdOptionConfig : xmlConfig.getCmdOptionConfigs()) {
        final OptionSpec.Builder optionSpecBuilder = OptionSpec.builder(cmdOptionConfig.getName())
            .paramLabel(cmdOptionConfig.getParamLabel())
            .type(Class.forName(cmdOptionConfig.getType()))
            .description(cmdOptionConfig.getDescription());
        if (cmdOptionConfig.getConverterType() != null) {
          optionSpecBuilder.converters(
              (ITypeConverter<?>) Class.forName(cmdOptionConfig.getConverterType())
                  .getDeclaredConstructors()[0].newInstance());
        }
        spec.addOption(optionSpecBuilder.build());
      }

      spec.addOption(OptionSpec.builder("file")
          .paramLabel("File input name")
          .type(String.class)
          .description("File input name")
          .build());

      final CommandLine commandLine = new CommandLine(spec);

      // set an execution strategy (the run(ParseResult) method) that will be called
      // by CommandLine.execute(args) when user input was valid
      commandLine.setExecutionStrategy(SearchCommand::run);

      commandLine.setExecutionExceptionHandler(new PrintExceptionMessageHandler());
      final int exitCode = commandLine.execute(args);
      System.exit(exitCode);
      // Exception handling: Print to the console error messages, not the entire stack trace
    } catch (IOException | InstantiationException | IllegalAccessException ex) {
      log.error(ex.getMessage(), ex);
      System.out.println("Error when reading input file: " + ex.getMessage());
    } catch (ClassNotFoundException | InvocationTargetException typeException) {
      log.error(typeException.getMessage(), typeException);
      System.out.println(
          "ProcessorType or Option Data Type is misconfigured: " + typeException.getMessage());
    } catch (Exception exception) {
      log.error(exception.getMessage(), exception);
      System.out.println("Unknown exception: " + exception.getMessage());
    }
    System.exit(ExitCode.FAILURE.getExitCode());
  }
}
