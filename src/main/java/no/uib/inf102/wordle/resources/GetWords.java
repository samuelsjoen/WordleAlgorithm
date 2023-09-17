package no.uib.inf102.wordle.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.uib.inf102.util.ReadFile;

/**
 * This class contains the list of guess words and answer words in Wordle.
 */
public class GetWords {

    private static final String FOLDER_PATH = "src/main/java/no/uib/inf102/wordle/resources";
    private static final String GUESS_WORDS_PATH = FOLDER_PATH + "/guessWords.txt";
    private static final String ANSWER_WORDS_PATH = FOLDER_PATH + "/answerWords.txt";

    public static final int WORD_LENGTH = 5;

    // Guess words
    /**
     * These words are words that can be guessed in a game of Wordle.
     * All words will have a length equal to WORD_LENGTH
     */
    private static final List<String> GUESS_WORDS_LIST = toList(GUESS_WORDS_PATH);
    private static final Set<String> GUESS_WORDS_SET = toSet(GUESS_WORDS_LIST);

    // Answer words
    /**
     * These words are a limited subset of the guess words. 
     * This is to make the game a little simpler so you don't have to answer very obscure words.
     * All words will have a length equal to WORD_LENGTH
     */
    public static final List<String> ANSWER_WORDS_LIST = toList(ANSWER_WORDS_PATH);
    public static final Set<String> ANSWER_WORDS_SET = toSet(ANSWER_WORDS_LIST);

    /**
     * These words are words that can be guessed in a game of Wordle.
     * All words will have a length equal to WORD_LENGTH
     * Every word in ANSWER_WORDS_LIST will be in ALL_WORDS_LIST
     */
    public static final List<String> ALL_WORDS_LIST = getAllWords();
    public static final Set<String> ALL_WORDS_SET = new HashSet<>(ALL_WORDS_LIST);

    /**
     * Provides a sorted list of the words from both the guess and answer lists.
     * @return
     */
    private static List<String> getAllWords() {
    	List<String> allWords = ReadFile.readLines(GUESS_WORDS_PATH);
    	for(String word : ANSWER_WORDS_SET) {
    		if(!GUESS_WORDS_SET.contains(word)) {
    			allWords.add(word);
    		}
    	}
        Collections.sort(allWords);
        return Collections.unmodifiableList(allWords);
    }

    private static List<String> toList(String path) {
		return Collections.unmodifiableList(ReadFile.readLines(path));
	}

	private static Set<String> toSet(List<String> list) {
		return Collections.unmodifiableSet(new HashSet<String>(list));
	}

	/**
     * Checks if the given wordGuess is part of the set of all Wordle words.
     * @param wordGuess
     * @return
     */
	public static boolean isLegalGuess(String wordGuess) {
		return ALL_WORDS_SET.contains(wordGuess.toLowerCase());
	}

	/**
     * Checks if the given wordGuess is part of the set of all Wordle words.
     * @param wordGuess
     * @return
     */
	public static boolean isLegalAnswer(String wordGuess) {
		return ANSWER_WORDS_SET.contains(wordGuess.toLowerCase());
	}

}
