package com.lumera.wordsearch

import java.io.File

/**
 * A simple utility function for reading the word list file. Provided as an example of mixing Kotlin with Java.
 * @param filepath path to word list file
 */
fun readWordList(filepath: String) = File(filepath).readLines().map(String::trim)
