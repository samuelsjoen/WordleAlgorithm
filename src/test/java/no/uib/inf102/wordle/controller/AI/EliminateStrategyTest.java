package no.uib.inf102.wordle.controller.AI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import no.uib.inf102.wordle.model.word.WordleAnswer;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class EliminateStrategyTest {

	@Test
	void testComputeMakeGuess() {
		EliminateStrategy ai = new EliminateStrategy();
		ArrayList<WordleWord> feedback = new ArrayList<>();
		ArrayList<String> guesses = new ArrayList<>();
		WordleAnswer ans = new WordleAnswer("cloud");
		WordleWord lastFeedback = null;
		int rounds = 0;
		while(true) {
			String guess = ai.makeGuess(lastFeedback);
			assertFalse(guesses.contains(guess),"should not guess same word twice");
			//EliminateStrategy must always guess words compatible with previous feedback.
			for(WordleWord f : feedback) {
				assertTrue(WordleWord.isPossibleWord(guess, f),"Guess: "+guess +" not compatible with "+f);
			}
			lastFeedback = ans.makeGuess(guess);
			if(lastFeedback.allMatch())
				break;
			feedback.add(lastFeedback);
			rounds++;
			//When EliminateStrategy guesses a letter that becomes grey or yellow 
			//meaning one should never guess same letter again unless it is green 
			assertTrue(rounds<=26);
		}
	}
	
}
