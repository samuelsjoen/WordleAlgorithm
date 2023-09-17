package no.uib.inf102.wordle.model.word;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import no.uib.inf102.wordle.resources.GetWords;

/**
 * This class represents a word given to the Wordle board. It is one row of
 * letters combined with the colors of the squares.
 * A Wordle Word is an iterable of WordleCharacter.
 */
public class WordleWord implements Iterable<WordleCharacter> {

    private List<WordleCharacter> word;

    /**
     * Creates a WordleWord by matching the characters in the given string to the given AnswerType array.
     * @param word
     * @param feedback
     */
    public WordleWord(String word, AnswerType[] feedback) {
    	if(word.length()!=feedback.length)
    		throw new IllegalArgumentException("word and feedback must have same length");
    	for(AnswerType type : feedback) {
    		if(type==AnswerType.BLANK) {
    			throw new IllegalArgumentException("Feedback can not contain BLANK");
    		}
    	}
    		
        this.word = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            WordleCharacter wordleChar = new WordleCharacter(c, feedback[i]);
            this.word.add(wordleChar);
        }
    }

    /**
     * Checks if word is legal by checking the list of legal Wordle words.
     * 
     * @return true if word is legal, false if not.
     */
    public boolean isLegalWord() {
        return GetWords.ALL_WORDS_SET.contains(getWordString().toLowerCase());
    }

    /**
     * Checks if all WordleCharacters have the answer type CORRECT
     * 
     * @return true if all corret, false if not
     */
    public boolean allMatch() {
        for (WordleCharacter wordleChar : this) {
            if (wordleChar.answerType != AnswerType.CORRECT)
                return false;
        }
        return true;
    }

    /**
     * Checks if <code>c</code> is in the WordelWord
     * 
     * @param c
     * @return true if the word contains the character, false if not
     */
    public boolean contains(Character c) {
        for (WordleCharacter wordleChar : this) {
            if (wordleChar.letter == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the WordleWord as a String without AnswerType.
     * @return
     */
    public String getWordString() {
        StringBuilder sb = new StringBuilder();
        for (WordleCharacter wordleChar : word) {
            sb.append(wordleChar.letter);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WordleCharacter wordleChar : word) {
            sb.append("(");
            sb.append(wordleChar.letter);
            sb.append(", ");
            sb.append(wordleChar.answerType);
            sb.append(") ");
        }
        return sb.toString();
    }

    @Override
    public Iterator<WordleCharacter> iterator() {
        return word.iterator();
    }

    /**
     * Check if word is legal given the feedback. Cheks that all letters that we
     * know the position of is in the word, and that all correctly placed letters
     * are in that position.
     * 
     * @param word
     * @param feedback
     * @return true if the word adheres to the feedback
     */
    public static boolean isPossibleWord(String word, WordleWord feedback) {
        WordleWord otherFeedback = WordleAnswer.matchWord(feedback.getWordString(), word);
        return otherFeedback.equals(feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WordleWord other = (WordleWord) obj;
        return Objects.equals(word, other.word);
    }

}
