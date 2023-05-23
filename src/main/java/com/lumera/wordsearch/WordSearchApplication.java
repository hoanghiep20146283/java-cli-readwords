package com.lumera.wordsearch;

import com.lumera.wordsearch.command.SearchCommand;
import com.lumera.wordsearch.config.XmlConfig;
import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;

/**
 * Entry point of application.
 */
public class WordSearchApplication {
//    public static void main(String[] args) {
//        var words = readWordList("wordlist.txt");
//        // ToDo: implement more
//    }-

  public static XmlConfig xmlConfig;

  public static void main(String[] args) {
//    CommandLine cmd = new CommandLine(new SearchCommand());
//    cmd.setCaseInsensitiveEnumValuesAllowed(true)
//        .setOut(myOutWriter()) .setErr(myErrWriter())  .setColorScheme(myColorScheme());
//    int exitCode = cmd.execute(args);
//    System.exit(exitCode);

    // TODO: handle exceptions when reading config file
    InputStream inputStream = WordSearchApplication.class.getClassLoader()
        .getResourceAsStream("config.yml");

    Yaml yaml = new Yaml(new Constructor(XmlConfig.class));
    xmlConfig = (XmlConfig) yaml.load(inputStream);

    CommandSpec spec = CommandSpec.create();
    spec.mixinStandardHelpOptions(true);

    xmlConfig.getCmdOptionConfigs().forEach(cmdOptionConfig -> {
      try {
        spec.addOption(OptionSpec.builder(cmdOptionConfig.getName())
            .paramLabel(cmdOptionConfig.getParamLabel())
            .type(Class.forName(cmdOptionConfig.getType()))
            .description("Max length of the words").build());
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    });

    CommandLine commandLine = new CommandLine(spec);
    // set an execution strategy (the run(ParseResult) method) that will be called
    // by CommandLine.execute(args) when user input was valid
    commandLine.setExecutionStrategy(SearchCommand::run);
    int exitCode = commandLine.execute(args);
    System.exit(exitCode);
  }
}
