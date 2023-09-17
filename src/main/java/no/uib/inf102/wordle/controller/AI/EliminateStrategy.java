package no.uib.inf102.wordle.controller.AI;

import java.util.Random;

import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

/**
 * This strategy eliminates guesses that are impossible with the feedback given
 * throughout the game.
 * <br>
 * </br>
 * For example:
 * If the answer is "break" and you answer "chest", you will get
 * feedback showing that the middle "e" is in the right position. Therefore you
 * eliminate all words that do not have an e in the middle position.
 */
public class EliminateStrategy implements IStrategy {

    private Random random = new Random();
    private WordleWordList guesses;

    public EliminateStrategy() {
    	reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback != null) {
        	guesses.eliminateWords(feedback);
        }

        // Choose a random word from the possible guesses after eliminating
        int randIndex = random.nextInt(guesses.size());
        return  guesses.possibleAnswers().get(randIndex);
    }

    @Override
    public void reset() {
    	guesses = new WordleWordList();
    }
}
