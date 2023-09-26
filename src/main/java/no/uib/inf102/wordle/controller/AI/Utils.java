package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    private static String bestWord = "";
    private static int bestScore = 0;
    /**
     * Goes through every word in possibleanswers and assigns a score based on the
     * regularity of the characters in the word compared to the entire list of
     * possibleAnswers.
     */
    public static String getBestWord(List<String> possibleAnswers) {
        reset();
        HashMap<Character, Integer> charCount = makeHashMap(possibleAnswers);

        for (String word : possibleAnswers) {
            int score = getScore(word, charCount);
            if (score > bestScore) {
                bestScore = score;
                bestWord = word;
            }
        }
        return bestWord;
    }

    /**
     * Goes through every word in possibleanswers and assigns a score based on the
     * regularity of the characters in the word compared to the entire list of
     * possibleAnswers. In addition it finds the word with least chars in common with skipChars
     */
    public static String getBestWord(List<String> possibleAnswers, String skipChars) {
        reset();
        HashMap<Character, Integer> charCount = makeHashMap(possibleAnswers);
        int leastChars = 5;
        for (String word: possibleAnswers) {
            int duplicateChars = containsChars(word, skipChars);
            int score = getScore(word, charCount);
            if (score > bestScore && duplicateChars <= leastChars) {
                bestScore = score;
                bestWord = word;
                leastChars = duplicateChars;
            }
        }
        return bestWord;
    }

    private static int containsChars(String word, String skipChars) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < skipChars.length(); j++) {
                if (word.charAt(i) == skipChars.charAt(j)) {
                    count+=1;
                }
            }
        }
        return count;
    }

    private static int getScore(String word, HashMap<Character, Integer> charCount) {
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
        return score;
    }

    private static void reset() {
        bestScore = 0;
        bestWord = "";
    }

    /**
     * Makes a hashmap of all characters and the number of times they appear in
     * total for the list of possible answers
     */
    public static HashMap<Character, Integer> makeHashMap(List<String> possibleAnswers) {
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
    private static void addWordToMap(String word, HashMap<Character, Integer> charCount) {
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            charCount.put(currentChar, charCount.getOrDefault(currentChar, 0) + 1);
        }
    }
}
