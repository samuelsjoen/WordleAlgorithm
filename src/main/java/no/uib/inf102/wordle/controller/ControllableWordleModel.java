package no.uib.inf102.wordle.controller;

import no.uib.inf102.wordle.model.GameState;
import no.uib.inf102.wordle.model.word.WordleWord;

public interface ControllableWordleModel {

    /**
     * Remove character of current guess. 
     * @return true if character was removed, false if not
     */
    boolean removeCharacter();
    
    /**
     * Add character to current guess.
     * @param c
     * @return true if character was added, false if not
     */
    boolean addCharacter(char c);

    /**
     * Lock in current guess (the text given). 
     * @throws IllegalArgumentException if current guess is not a legal guess.
     * @return guess feedback. WordleWord value with the same letters, but each letter is given a new answertype (wether the character was correct, wrong position or wrong).
     */
    WordleWord makeGuess() throws IllegalArgumentException;
 
    /**
     * Get current game state. The game state expresses if the game is in active, game is over, etc.
     * @return current game state.
     */
    GameState getGameState();

    /**
     * Get number of miliseconds timer delay.
     * @return timer delay
     */
    int getTimerDelay();

    /**
     * Execute operations that should happen for each clock tick
     */
    void clockTick();

    /**
     * Reset game to initial state. 
     * Clear board, clear current guess, etc.
     */
    void reset();

}
