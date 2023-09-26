package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.List;

import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyStrategy implements IStrategy {

    private WordleWordList guesses;

    public MyStrategy() {
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {

        List<String> possibleAnswers = guesses.possibleAnswers();
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeGuess'");
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
    }

}
