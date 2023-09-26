package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.uib.inf102.wordle.model.word.WordleCharacter;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyStrategy implements IStrategy {

    private WordleWordList guesses;
    private int guessCount;

    public MyStrategy() {
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {

        List<String> possibleAnswers = guesses.possibleAnswers();

        if (feedback == null) {
            guessCount = 0;
        }

        if (guessCount == 1) {
            List<String> oldPossibleAnswers = new ArrayList<String>(possibleAnswers);
            guesses.eliminateWords(feedback);
            if (possibleAnswers.size() > 10) {
                possibleAnswers = oldPossibleAnswers;
            }
            String guess = Utils.getBestWord(possibleAnswers, feedback.getWordString());
            guesses.eliminateWords(feedback);
            guessCount += 1;
            return guess;
        }

        guessCount += 1;
        return Utils.getBestWord(possibleAnswers);
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
        guessCount = 0;
    }

}
