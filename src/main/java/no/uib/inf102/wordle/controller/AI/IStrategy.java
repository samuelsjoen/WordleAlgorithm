package no.uib.inf102.wordle.controller.AI;

import no.uib.inf102.wordle.model.word.WordleWord;

public interface IStrategy {
    
    /**
     * Make a Wordle guess based on the given <code>feedback</code>.
     * @param feedback
     * @return the guess
     */
    String makeGuess(WordleWord feedback);

    /**
     * This method is called when there is a new word to guess.
     * It should reset any internal variables to make guesses for a new word.
     */
    void reset();

}
