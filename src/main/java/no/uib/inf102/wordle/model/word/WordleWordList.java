package no.uib.inf102.wordle.model.word;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import no.uib.inf102.wordle.resources.GetWords;

/**
 * This class describes a structure of two lists for a game of Wordle: The list
 * of words that can be used as guesses and the list of words that can be
 * possible answers.
 */
public class WordleWordList {

	/**
	 * All words in the game. These words can be used as guesses.
	 */
	private List<String> allWords;

	/**
	 * A subset of <code>allWords</code>. <br>
	 * </br>
	 * These words can be the answer to a wordle game.
	 */
	private List<String> possibleAnswers;

	/**
	 * Create a WordleWordList that uses the full words and limited answers of the
	 * GetWords class.
	 */
	public WordleWordList() {
		this(GetWords.ALL_WORDS_LIST, GetWords.ANSWER_WORDS_LIST);
	}

	/**
	 * Create a WordleWordList with the given <code>words</code> as both guesses and
	 * answers.
	 * 
	 * @param words
	 */
	public WordleWordList(List<String> words) {
		this(words, words);
	}

	/**
	 * Create a WordleWordList with the given lists as guessing words and answers.
	 * <code>answers</code> must be a subset of <code>words</code>.
	 * 
	 * @param words   The list of words to be used as guesses
	 * @param answers The list of words to be used as answers
	 * @throws IllegalArgumentException if the given <code>answers</code> were not a
	 *                                  subset of <code>words</code>.
	 */
	public WordleWordList(List<String> words, List<String> answers) {
		HashSet<String> wordsSet = new HashSet<String>(words);
		if (!wordsSet.containsAll(answers))
			throw new IllegalArgumentException("The given answers were not a subset of the valid words.");

		this.allWords = new ArrayList<>(words);
		this.possibleAnswers = new ArrayList<>(answers);
	}

	/**
	 * Get the list of all guessing words.
	 * 
	 * @return all words
	 */
	public List<String> getAllWords() {
		return allWords;
	}

	/**
	 * Returns the list of possible answers.
	 * 
	 * @return
	 */
	public List<String> possibleAnswers() {
		return possibleAnswers;
	}

	/**
	 * Eliminates words from the possible answers list using the given
	 * <code>feedback</code>
	 * 
	 * @param feedback
	 */
	public void eliminateWords(WordleWord feedback) {
		Iterator<String> iterator = possibleAnswers.iterator();

		while (iterator.hasNext()) {
			String word = (String) iterator.next();
			if (WordleWord.isPossibleWord(word, feedback) != true) {
				iterator.remove();
			}
		}
	}

	/**
	 * Returns the amount of possible answers in this WordleWordList
	 * 
	 * @return size of
	 */
	public int size() {
		return possibleAnswers.size();
	}

	/**
	 * Removes the given <code>answer</code> from the list of possible answers.
	 * 
	 * @param answer
	 */
	public void remove(String answer) {
		possibleAnswers.remove(answer);

	}

	/**
	 * Returns the word length in the list of valid guesses.
	 * 
	 * @return
	 */
	public int wordLength() {
		return allWords.get(0).length();
	}
}
