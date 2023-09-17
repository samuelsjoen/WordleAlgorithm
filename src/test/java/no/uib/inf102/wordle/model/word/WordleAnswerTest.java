package no.uib.inf102.wordle.model.word;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import no.uib.inf102.wordle.resources.GetWords;

public class WordleAnswerTest {

    private Random random = new Random();

    private final Set<String> ALL_WORDS = GetWords.ALL_WORDS_SET;
    private final Set<String> ANSWER_WORDS = GetWords.ANSWER_WORDS_SET;
    private final String LEGAL_WORD = "arise";

    @Test
    public void allCorrectFeedback() {
        WordleAnswer answer = new WordleAnswer(LEGAL_WORD);

        WordleWord feedback = answer.makeGuess(LEGAL_WORD);
        for (WordleCharacter c : feedback) {
            assertEquals(AnswerType.CORRECT, c.answerType);
        }
    }

    @Test
    public void allWrongPositionFeedback() {
        String answerString = "coast";
        String wrongPositionAnswerString = "tacos";
        WordleAnswer answer = new WordleAnswer(answerString);

        WordleWord feedback = answer.makeGuess(wrongPositionAnswerString);
        for (WordleCharacter c : feedback) {
            assertEquals(AnswerType.WRONG_POSITION, c.answerType);
        }
    }

    @Test
    public void allWrongFeedback() {
        String answerString = "coast";
        String wrongPositionAnswerString = "hurry";
        WordleAnswer answer = new WordleAnswer(answerString);

        WordleWord feedback = answer.makeGuess(wrongPositionAnswerString);
        for (WordleCharacter c : feedback) {
            assertEquals(AnswerType.WRONG, c.answerType);
        }
    }

    @Test
    public void partialCorrectFeedback() {
        String answerString = "carry";
        String wrongPositionAnswerString = "hurry";
        WordleAnswer answer = new WordleAnswer(answerString);

        WordleWord feedback = answer.makeGuess(wrongPositionAnswerString);
        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i < 2) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            else {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
    }

    @Test
    public void correctSubstitutesWrongPosition() {
        WordleAnswer answer = new WordleAnswer("beast");
        WordleWord feedback = answer.makeGuess("adapt");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG, c.answerType, "There is an 'a' in the answer, but it is located later at index 2. This 'a' should be WRONG.");
            }
            if (i == 1) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
    }

    @Test
    public void isPossibleWordRocks() {
        WordleAnswer answer = new WordleAnswer("rocks");
        WordleWord feedback = answer.makeGuess("sores");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG, c.answerType, "The first 's' should be WRONG. There is only one 's' in 'rocks'. The second 's' should be CORRECT.");
            }
            if (i == 1) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.WRONG_POSITION, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("rocks", feedback));
        assertFalse(WordleWord.isPossibleWord("sores", feedback));
    }

    @Test
    public void isPossibleWordPoppyGuess() {
        WordleAnswer answer = new WordleAnswer("upper");
        WordleWord feedback = answer.makeGuess("poppy");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG_POSITION, c.answerType);
            }
            if (i == 1) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("upper", feedback));
        assertFalse(WordleWord.isPossibleWord("poppy", feedback));
    }

    @Test
    public void isPossibleWordApoopGuess() {
        WordleAnswer answer = new WordleAnswer("poppy");
        WordleWord feedback = answer.makeGuess("apoop");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 1) {
                assertEquals(AnswerType.WRONG_POSITION, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.WRONG_POSITION, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.WRONG_POSITION, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("poppy", feedback));
        assertFalse(WordleWord.isPossibleWord("apoop", feedback));
    }

     @Test
    public void isPossibleWordMommy() {
        WordleAnswer answer = new WordleAnswer("mommy");
        WordleWord feedback = answer.makeGuess("money");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 1) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("mommy", feedback));
        assertTrue(WordleWord.isPossibleWord("mossy", feedback));
    }

    @Test
    public void canCreateLegalWords() {
        for (String legalAnswerWord : ANSWER_WORDS) {
            assertDoesNotThrow(() -> new WordleAnswer(legalAnswerWord), "This word was not legal: " + legalAnswerWord);
        }
    }

    
    @Test
    public void cannotGuessNonsenseWords() {
        WordleAnswer answer = new WordleAnswer(LEGAL_WORD);
        for (int i = 0; i < 1000; i++) {
            assertThrows(IllegalArgumentException.class, () -> answer.makeGuess(createNonsenseWord()));
        }
    }

    /**
     * Creates a word with a random jumble of characters
     * 
     * @return nonsense word
     */
    private String createNonsenseWord() {
        int a = 97;
        int z = 122;
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < GetWords.WORD_LENGTH; i++) {
                char c = (char) random.nextInt(a, z);
                sb.append(c);
            }
            String word = sb.toString();
            if (!ALL_WORDS.contains(word))
                return word;
        }
    }


}
