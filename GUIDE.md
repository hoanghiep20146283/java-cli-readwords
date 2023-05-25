# The word search

This is a home assignment used during the recruitment process at Lumera. This version is specific to Java and Kotlin 
developers - you are free to use either language. Your task is to implement a command line tool for lookups of words in 
a word dictionary file.

## Searching for matching words

The words search tool searches the file *wordlist.txt* for words matching a word query. The "word query" consist of one
or more rules that specify the attributes that must be fulfilled for the returned words (logical AND). Each rule is
given a single argument.

## Rules to implement

The following rules should be implemented:

    class={isogram|palindrome|semordnilap}

    maxlength=<INT>

    minlength=<INT>

    startswith=<head>

    endswith=<tail>

    containsonly=<characters>

Note - an *isogram* is a word where no character is used more than once. A *palindrome* is a word that reads the same
backwards. A *semordnilap* is a word that spells a different word backwards (a different word from the list).

### Examples

* "class=palindrome maxlength=8" would return all palindromes that are at most eight letters long.
* "maxlength=3 minlength=3" would return all words that are exactly three letters long.
* "endswith=abc endswith=bca" would return no words.
* "startswith=ba" would return all words that start with the letters BA - like BAnana and BAth.
* "containsonly=abcde" would return all words that are made up of the letters A, B, C, D and E - like "bad" and "decade".
### Implementation

You should implement an extensible command line tool - it should be easy to introduce new rules later. The tool should
use input from the command line arguments to search the contents of wordlist.txt, and output matching words to stdout.

## General guidelines

The task is expected to be implemented in roughly 2-5 hours. Zip your complete project directory (including build
scripts, source code and test resources) and send the zip file to us for evaulation. When evaulating the assignment, we
will look at:

* Patterns and structure of the code.
* Correctness of the solution.
* Robustness of the solution (error handling, etc.).
* Readability of the code.
* Extensibility.
* Test cases and testability of the code. We do not expect full test coverage, but a few test cases to show how test
  cases are implemented.

Good luck!
