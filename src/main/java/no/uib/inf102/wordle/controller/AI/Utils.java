package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.List;

public class Utils {
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
