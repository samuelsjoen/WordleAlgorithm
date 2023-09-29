package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AIUtils {

    private static String bestWord = "";
    private static int bestScore = 0;

    /**
     * Goes through every word in possibleanswers and assigns a score based on the
     * regularity of the characters in the word compared to the entire list of
     * possibleAnswers.
     */
    public static String getBestWord(List<String> possibleAnswers) {
        reset();
        HashMap<Character, Integer> charCount = makeHashMap(possibleAnswers); // O(mk)
        for (String word : possibleAnswers) { // O(mk)
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
     * possibleAnswers. In addition it finds the word with least chars in common
     * with skipChars.
     */
    public static String getBestWord(List<String> possibleAnswers, String skipChars) {
        reset();
        HashMap<Character, Integer> charCount = makeHashMap(possibleAnswers); 
        int leastChars = 5;
        for (String word : possibleAnswers) {
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

    /**
     * An improved version of getBestWord which also assigns a score based on the
     * regularity of the characters in the word compared to the entire list of
     * possibleAnswers.
     * However unlike getBestWord, this also assigns a score based on the index of
     * each character.
     */
    public static String getBetterBestWord(List<String> possibleAnswers) {
        reset();
        HashMap<Integer, HashMap<Character, Integer>> charCount = makeIndexedHashMap(possibleAnswers);
        for (String word : possibleAnswers) {
            int score = getIndexedScore(word, charCount);
            if (score > bestScore) {
                bestScore = score;
                bestWord = word;
            }
        }
        return bestWord;

    }

    /**
     * An improved version of getBestWord which also assigns a score based on the
     * regularity of the characters in the word compared to the entire list of
     * possibleAnswers.
     * However unlike getBestWord, this also assigns a score based on the index of
     * each character. The returned word is also as unique as possible compared to the
     * String skipChar.
     */
    public static String getBetterBestWord(List<String> possibleAnswers, String skipChars) {
        reset();
        HashMap<Integer, HashMap<Character, Integer>> charCount = makeIndexedHashMap(possibleAnswers);
        int leastChars = 5;
        for (String word : possibleAnswers) {
            int duplicateChars = containsChars(word, skipChars);
            int score = getIndexedScore(word, charCount);
            if (score > bestScore && duplicateChars <= leastChars) {
                bestScore = score;
                bestWord = word;
                leastChars = duplicateChars;
            }
        }
        return bestWord;

    }

    /** Checks how many characters from word are also in skipChars. */
    private static int containsChars(String word, String skipChars) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < skipChars.length(); j++) {
                if (word.charAt(i) == skipChars.charAt(j)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /** Returns the score of a character. */
    private static int getScore(String word, HashMap<Character, Integer> charCount) {
        int score = 0;
        ArrayList<Character> usedChars = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) { // O(k)
            Character currentChar = word.charAt(i);
            int characterScore = charCount.get(currentChar);
            if (!usedChars.contains(currentChar)) {
                usedChars.add(currentChar);
                score += characterScore;
            } else {
                score += characterScore / 2;
            }
        }
        return score;
    }

    /** Returns the score of a character based on the index. */
    private static int getIndexedScore(String word, HashMap<Integer, HashMap<Character, Integer>> charCount) {
        int score = 0;
        ArrayList<Character> usedChars = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            Character currentChar = word.charAt(i);
            HashMap<Character, Integer> indexMap = charCount.get(i);
            int characterScore = indexMap.get(currentChar);
            if (!usedChars.contains(currentChar)) {
                usedChars.add(currentChar);
                score += characterScore;
            } else {
                score += characterScore / 2;
            }
        }
        return score;
    }

    /**
     * Makes a hashmap of all characters and the number of times they appear in
     * total for the list of possible answers.
     */
    private static HashMap<Character, Integer> makeHashMap(List<String> possibleAnswers) {
        HashMap<Character, Integer> charCount = new HashMap<>();

        for (String word : possibleAnswers) { // O(m)
            addWordToMap(word, charCount); // O(k)
        }
        return charCount;
    }

    /**
     * Takes in a word and updates the character count in the hashmap for each
     * character appearing in the word.
     */
    private static void addWordToMap(String word, HashMap<Character, Integer> charCount) {
        for (int i = 0; i < word.length(); i++) { // O(k)
            char currentChar = word.charAt(i);
            charCount.put(currentChar, charCount.getOrDefault(currentChar, 0) + 1);
        }
    }

    /**
     * Makes a hashmap of all characters and the number of times they appear in
     * total for the list of
     * possible characters based on the index they appear at.
     */
    private static HashMap<Integer, HashMap<Character, Integer>> makeIndexedHashMap(List<String> possibleAnswers) {
        HashMap<Integer, HashMap<Character, Integer>> charCount = new HashMap<>();
        for (String word : possibleAnswers) {
            addWordToIndexedMap(word, charCount);
        }
        return charCount;
    }

    /** Takes in a word and updates the character count for each index. */
    private static void addWordToIndexedMap(String word, HashMap<Integer, HashMap<Character, Integer>> charCount) {
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                char currentChar = word.charAt(i);
                HashMap<Character, Integer> indexCharCount = charCount.get(i);
                if (indexCharCount == null) {
                    indexCharCount = new HashMap<Character, Integer>();

                }
                indexCharCount.put(currentChar, indexCharCount.getOrDefault(currentChar, 0) + 1);
                charCount.put(i, indexCharCount);
            }
        }
    }

    /** Resets variables. */
    private static void reset() {
        bestScore = 0;
        bestWord = "";
    }
}
