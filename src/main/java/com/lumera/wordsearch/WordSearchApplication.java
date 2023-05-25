package com.lumera.wordsearch;

import com.lumera.wordsearch.command.SearchCommand;
import com.lumera.wordsearch.config.XmlConfig;
import com.lumera.wordsearch.config.XmlConfig.CmdOptionConfig;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;

/**
 * Entry point of application.
 */
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

      final CommandLine commandLine = new CommandLine(spec);
      // set an execution strategy (the run(ParseResult) method) that will be called
      // by CommandLine.execute(args) when user input was valid
      commandLine.setExecutionStrategy(SearchCommand::run);
      final int exitCode = commandLine.execute(args);
      System.exit(exitCode);
    } catch (ClassNotFoundException | IOException | InstantiationException |
             IllegalAccessException | InvocationTargetException ex) {
      // TODO: logging
    }
  }
}
