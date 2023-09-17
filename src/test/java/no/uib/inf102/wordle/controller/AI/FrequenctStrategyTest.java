package no.uib.inf102.wordle.controller.AI;

import static no.uib.inf102.wordle.model.word.AnswerType.WRONG;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import no.uib.inf102.wordle.model.word.AnswerType;
import no.uib.inf102.wordle.model.word.WordleAnswer;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;
import no.uib.inf102.wordle.resources.GetWords;

public class FrequenctStrategyTest {

	@Test
	public void makeGuessBetterThanSaree() {
		FrequencyStrategy ai = new FrequencyStrategy();
		String guess = ai.makeGuess(null);
		int count = countGreen(guess);
		assertTrue(GetWords.isLegalGuess(guess),"You must guess a word from the dictionary.");
		if(GetWords.isLegalGuess("saree")) {
			int expectedCount = countGreen("saree");
			assertTrue(expectedCount>=count, "The word saree gave more green hits than "+guess);
		}
		WordleAnswer ans = new WordleAnswer("berry");
	}

	public int countGreen(String guess) {
		int count=0;
		for(String word : GetWords.ALL_WORDS_LIST) {
			for(int i=0; i<guess.length(); i++) {
				if(word.charAt(i)==guess.charAt(i))
					count++;
			}
		}
		return count;
	}

	@Test
	public void makeGuessafterScore() {
		AnswerType[] oneYellow = {WRONG,AnswerType.WRONG_POSITION,WRONG,WRONG,WRONG};
		WordleWord feedback = new WordleWord("score", oneYellow);

		FrequencyStrategy ai = new FrequencyStrategy();
		String guess = ai.makeGuess(feedback);
		String good = "catch";
		
		assertTrue(GetWords.isLegalGuess(guess),"You must guess a word from the dictionary.");
				
		if(GetWords.isLegalGuess("catch")) {
			int count = countGreen(feedback, guess);
			int expectedCount = countGreen(feedback, "catch");
			assertTrue(expectedCount>=count, "The word catch gave more green hits than "+guess);
		}
	}

	public int countGreen(WordleWord feedback, String guess) {
		int count=0;
		for(String word : GetWords.ALL_WORDS_LIST) {
			if(WordleWord.isPossibleWord(word, feedback)) {
				for(int i=0; i<guess.length(); i++) {
					if(word.charAt(i)==guess.charAt(i))
						count++;
				}				
			}
		}
		return count;
	}
}
