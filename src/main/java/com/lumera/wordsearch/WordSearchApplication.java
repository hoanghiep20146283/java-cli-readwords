package com.lumera.wordsearch;

import static com.lumera.wordsearch.WordlistKt.readWordList;

import com.lumera.wordsearch.command.SearchCommand;
import picocli.CommandLine;

/**
 * Entry point of application.
 */
public class WordSearchApplication {
//    public static void main(String[] args) {
//        var words = readWordList("wordlist.txt");
//        // ToDo: implement more
//    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new SearchCommand());
//    cmd.setCaseInsensitiveEnumValuesAllowed(true)
//        .setOut(myOutWriter()) .setErr(myErrWriter())  .setColorScheme(myColorScheme());
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}
