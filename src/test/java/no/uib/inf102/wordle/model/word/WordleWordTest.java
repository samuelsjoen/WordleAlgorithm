package no.uib.inf102.wordle.model.word;

import static no.uib.inf102.wordle.model.word.AnswerType.WRONG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static no.uib.inf102.wordle.model.word.AnswerType.*;

import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import no.uib.inf102.wordle.resources.GetWords;

public class WordleWordTest {

    private final Set<String> WORDLE_WORDS = GetWords.ALL_WORDS_SET;
    private Random random = new Random();

    @Test
    public void createFullWord() {
    	AnswerType[] feedback = {WRONG,CORRECT,WRONG,WRONG_POSITION,WRONG};
    	String word = "arise";
    	WordleWord wordleWord = new WordleWord(word,feedback);
        int letterCount = 0;
        for (WordleCharacter wordleChar : wordleWord) {
            letterCount++;
        }
        assertEquals(word.length(), letterCount, "The number of letters in the word was wrong.");
        assertEquals(word, wordleWord.getWordString(), "The word returned by getWordString() must be the same as what was passed to the constructor.");
    }

    @Test
    public void sameLength() {
    	String word = "hello";
    	AnswerType[] shortFeedback = {WRONG,CORRECT,WRONG_POSITION,WRONG};
    	assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, shortFeedback));
    	AnswerType[] longFeedback = {WRONG,WRONG,CORRECT,WRONG_POSITION,WRONG,CORRECT};
    	assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, longFeedback));
    }

    @Test
    public void noBlank() {
    	String word = "hello";
    	AnswerType[] feedback = {WRONG,CORRECT,BLANK,WRONG_POSITION,WRONG};
    	assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, feedback));
    }

    @Test
    public void nonsenseWordsAreIllegal() {
    	AnswerType[] feedback = {WRONG,CORRECT,WRONG,WRONG_POSITION,WRONG};
        for (int i = 0; i < 1000; i++) {
            WordleWord word = new WordleWord(createNonsenseWord(),feedback);
            assertFalse(word.isLegalWord());
        }
    }

    /**
     * Creates a word with a random jumble of characters
     * 
     * @return nonsense word
     */
    private String createNonsenseWord() {
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < GetWords.WORD_LENGTH; i++) {
                char c = (char) random.nextInt('a', 'z'+1);
                sb.append(c);
            }
            String word = sb.toString();
            if (!WORDLE_WORDS.contains(word))
                return word;
        }
    }

    @Test
    public void matchAll() {
    	String word = "graph";
    	AnswerType[] correctFeedback = {CORRECT,CORRECT,CORRECT,CORRECT,CORRECT};
    	AnswerType[] wrongFeedback = {CORRECT,CORRECT,WRONG,CORRECT,CORRECT};
    	WordleWord correct = new WordleWord(word, correctFeedback);
    	assertTrue(correct.allMatch());
    	WordleWord wrong = new WordleWord(word, wrongFeedback);
    	assertFalse(wrong.allMatch());
    }

    @Test
    public void testContains() {
    	String word = "hello";
    	AnswerType[] feedback = {WRONG,CORRECT,CORRECT,WRONG_POSITION,WRONG};
    	WordleWord wordleWord = new WordleWord(word,feedback);
    	assertTrue(wordleWord.contains('h'));
    	assertTrue(wordleWord.contains('e'));
    	assertTrue(wordleWord.contains('l'));
    	assertTrue(wordleWord.contains('o'));
    	assertFalse(wordleWord.contains('a'));
    	assertFalse(wordleWord.contains('c'));
    	assertFalse(wordleWord.contains('f'));
    	assertFalse(wordleWord.contains('z'));
    	assertFalse(wordleWord.contains(' '));
    }
    
    @Test
    public void testEquals() {
    	String word = "hello";
    	AnswerType[] correctFeedback = {CORRECT,CORRECT,CORRECT,CORRECT,CORRECT};
    	AnswerType[] wrongFeedback = {WRONG_POSITION,CORRECT,WRONG,CORRECT,WRONG};
    	WordleWord correct = new WordleWord(word, correctFeedback);
    	WordleWord wrong = new WordleWord(word, wrongFeedback);
    	assertTrue(correct.equals(new WordleWord(word, correctFeedback)));
    	assertTrue(wrong.equals(new WordleWord(word, wrongFeedback)));
    	assertFalse(correct.equals(wrong));
    	assertTrue(wrong.equals(wrong));
    	assertTrue(correct.equals(correct));
    	assertFalse(correct.equals(null));
    	assertFalse(correct.equals(word));
    }
}
