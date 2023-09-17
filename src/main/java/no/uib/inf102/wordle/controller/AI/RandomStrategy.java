package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.resources.GetWords;

/**
 * This strategy guesses a new, random and legal word whether the word is a
 * possible answer or not.
 */
public class RandomStrategy implements IStrategy {

    private List<String> POSSIBLE_WORDS;
    private Random random;

    public RandomStrategy() {
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        return selectRandomLegalWord();
    }

    /**
     * Selects a random legal word from the wordle legal word list.
     * 
     * @return a random Wordle word
     */
    private String selectRandomLegalWord() {
        return removeWord();
    }

    /**
     * Removes a random word to be guessed from POSSIBLE_WORDS in O(1)
     * 
     * @return the removed word
     */
    private String removeWord() {
        int n = POSSIBLE_WORDS.size();
        int randomIndex = random.nextInt(n);
        int lastIndex = n - 1;

        Collections.swap(POSSIBLE_WORDS, randomIndex, lastIndex);
        return POSSIBLE_WORDS.remove(lastIndex);
    }

    @Override
    public void reset() {
        POSSIBLE_WORDS = new ArrayList<>(GetWords.ALL_WORDS_LIST);
        random = new Random();
    }

}
