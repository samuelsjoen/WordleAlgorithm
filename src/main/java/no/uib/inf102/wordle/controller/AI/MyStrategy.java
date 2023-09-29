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

        if (feedback != null) {
            guesses.eliminateWords(feedback);
        }
        if (feedback == null) {
            guessCount = 0;
        }
        if (guessCount == 3) {
            String guess = AIUtils.getBestWord(possibleAnswers, feedback.getWordString());
            if (guess != "") {
                return guess;
            }
        }

        guessCount += 1;
        return AIUtils.getBetterBestWord(possibleAnswers);
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
        guessCount = 0;
    }

}
