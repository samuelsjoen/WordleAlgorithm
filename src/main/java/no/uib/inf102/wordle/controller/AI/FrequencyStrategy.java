package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

/**
 * This strategy finds the word within the possible words which has the highest
 * expected
 * number of green matches.
 */
public class FrequencyStrategy implements IStrategy {

    private WordleWordList guesses;

    public FrequencyStrategy() {
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {

        if (feedback != null) {
            guesses.eliminateWords(feedback);
        }

        List<String> possibleAnswers = guesses.possibleAnswers();
        HashMap<Character, Integer> charCount = makeHashMap(possibleAnswers);
        String bestWord = "";
        int bestScore = 0;

        // Goes through every word in possibleanswers and assigns a score based on the
        // regularity of the characters in the word
        for (String word : possibleAnswers) {
            int score = 0;
            ArrayList<Character> usedChars = new ArrayList<>();
            for (int i = 0; i < word.length(); i++) {
                Character currentChar = word.charAt(i);
                if (!usedChars.contains(currentChar)) {
                    usedChars.add(currentChar);
                    int characterScore = charCount.get(currentChar);
                    score += characterScore;
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestWord = word;
            }
        }
        return bestWord;
    }

    /**
     * Makes a hashmap of all characters and the number of times they appear in
     * total for the list of possible answers
     */
    private HashMap<Character, Integer> makeHashMap(List<String> possibleAnswers) {
        HashMap<Character, Integer> charCount = new HashMap<>();

        for (String word : possibleAnswers) {
            addWordToMap(word, charCount);
        }
        return charCount;
    }

    /**
     * Takes in a word and updates the character count in the hashmap for each
     * character appearing in the word
     */
    private void addWordToMap(String word, HashMap<Character, Integer> charCount) {
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            charCount.put(currentChar, charCount.getOrDefault(currentChar, 0) + 1);
        }
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
    }
}