package no.uib.inf102.wordle.model.word;

import java.util.HashMap;
import java.util.Random;

import no.uib.inf102.wordle.resources.GetWords;

/**
 * This class represents an answer to a Wordle puzzle.
 * 
 * The answer must be one of the words in the LEGAL_WORDLE_LIST.
 */
public class WordleAnswer {

    private final String WORD;
    private static HashMap<Character, Integer> charMap;
    private static Random random = new Random();

    /**
     * Creates a WordleAnswer object with a given word.
     * 
     * @param answer
     */
    public WordleAnswer(String answer) {
        this.WORD = answer.toLowerCase();
        this.charMap = createCharMap(answer);
    }

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     */
    public WordleAnswer() {
        this(random);
    }

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     * using a specified random object.
     * This gives us the opportunity to set a seed so that tests are repeatable.
     */
    public WordleAnswer(Random random) {
        this(getRandomWordleAnswer(random));
    }

    /**
     * Gets a random wordle answer
     * 
     * @param random
     * @return
     */
    private static String getRandomWordleAnswer(Random random) {
        int randomIndex = random.nextInt(GetWords.ANSWER_WORDS_LIST.size());
        String newWord = GetWords.ANSWER_WORDS_LIST.get(randomIndex);
        return newWord;
    }

    /**
     * Guess the Wordle answer. Checks each character of the word guess and gives
     * feedback on which that is in correct position, wrong position and which is
     * not in the answer word.
     * This is done by updating the AnswerType of each WordleCharacter of the
     * WordleWord.
     * 
     * @param wordGuess
     * @return wordleWord with updated answertype for each character.
     */
    public WordleWord makeGuess(String wordGuess) {
        if (!GetWords.isLegalGuess(wordGuess))
            throw new IllegalArgumentException("The word '" + wordGuess + "' is not a legal guess");

        WordleWord guessFeedback = matchWord(wordGuess, WORD);
        return guessFeedback;
    }

    /**
     * Generates a WordleWord showing the match between <code>guess</code> and
     * <code>answer</code>
     * 
     * @param guess
     * @param answer
     * @return
     */
    public static WordleWord matchWord(String guess, String answer) {
        charMap = createCharMap(answer);
        int wordLength = answer.length();
        if (guess.length() != wordLength)
            throw new IllegalArgumentException("Guess and answer must have same number of letters but guess = " + guess
                    + " and answer = " + answer);

        AnswerType[] feedback = new AnswerType[5];
        for (int i = 0; i < wordLength; i++) {
            feedback[i] = getAnswerType(guess, answer, i);
        }
        return new WordleWord(guess, feedback);
    }

    /** Gets the AnswerType for a character at a certain index in the guess word */
    private static AnswerType getAnswerType(String guess, String answer, int index) {
        char currentChar = guess.charAt(index);
        if (currentChar == answer.charAt(index)) {
            if (isLegalCount(currentChar) == true) {
                return AnswerType.CORRECT;
            }
        } else {
            for (int answerIndex = 0; answerIndex < answer.length(); answerIndex++) {
                if (currentChar == answer.charAt(answerIndex)) {
                    if (guess.charAt(answerIndex) != answer.charAt(answerIndex)) {
                        if (isLegalCount(currentChar) == true) {
                            return AnswerType.WRONG_POSITION;
                        }
                    }
                }
            }
        }
        return AnswerType.WRONG;
    }

    /** Creates a HashMap of all characters in the answer and the amount of times
     * they appear.
     */
    private static HashMap<Character, Integer> createCharMap(String answer) {
        HashMap<Character, Integer> map = new HashMap<>();
        int count;
        for (int index = 0; index < answer.length(); index++) {
            char currentChar = answer.charAt(index);
            if (map.get(currentChar) == null) {
                count = 0;
            } else {
                count = map.get(currentChar);
            }
            map.put(currentChar, count + 1);
        }
        return map;
    }

    /**
     * Makes sure that a given character outputs CORRECT or WRONG_POSITION
     * only as many times as it appears in the answer.
     */
    private static boolean isLegalCount(Character c) {
        int count;
        if (charMap.get(c) == null) {
            count = 0;
        } else {
            count = charMap.get(c);
        }
        updateCharMap(c);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /** Updates the amount a character has been counted */
    private static void updateCharMap(Character c) {
        int count;
        if (charMap.get(c) == null) {
            count = 0;
        } else {
            count = charMap.get(c);
        }
        charMap.put(c, count - 1);
    }
}
