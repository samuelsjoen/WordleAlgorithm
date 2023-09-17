package no.uib.inf102.wordle.model.word.wordleWordList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf102.util.ReadFile;
import no.uib.inf102.wordle.model.word.WordleAnswer;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class WordleWordListTest {

    private static final String FOLDER_PATH = "src/test/java/no/uib/inf102/wordle/model/word/wordleWordList/";

    //Note that these tests may fail if there are any changes to the dictionary
    @Test
    public void eliminateWords1() {
        WordleAnswer answer = new WordleAnswer("rocks");
        WordleWordList list = new WordleWordList();
        WordleWord feedback = answer.makeGuess("carry");

        list.eliminateWords(feedback);
        List<String> actualList = list.possibleAnswers();
        List<String> expectedList = ReadFile.readLines(FOLDER_PATH + "eliminateWords1.txt");
        assertEquals(expectedList, actualList, "The number of words eliminated was not correct.");

        Collections.sort(actualList);
        Collections.sort(expectedList);

        for (int i = 0; i < actualList.size(); i++) {
            String actual = actualList.get(i);
            String expected = expectedList.get(i);
            assertEquals(expected, actual, "The remaining words after elimination were not the same.");
        }
    }

    @Test
    public void eliminateWords2() {
        WordleAnswer answer = new WordleAnswer("rocks");
        WordleWordList list = new WordleWordList();
        WordleWord feedback = answer.makeGuess("apoop");

        list.eliminateWords(feedback);
        List<String> actualList = list.possibleAnswers();
        List<String> expectedList = ReadFile.readLines(FOLDER_PATH + "eliminateWords2.txt");
        assertEquals(expectedList, actualList, "The number of words eliminated was not correct.");

        Collections.sort(actualList);
        Collections.sort(expectedList);

        for (int i = 0; i < actualList.size(); i++) {
            String actual = actualList.get(i);
            String expected = expectedList.get(i);
            assertEquals(expected, actual, "The remaining words after elimination were not the same.");
        }
    }

    @Test
    public void eliminateWords3() {
        WordleAnswer answer = new WordleAnswer("rocks");
        WordleWordList list = new WordleWordList();
        WordleWord feedback = answer.makeGuess("arise");

        list.eliminateWords(feedback);
        List<String> actualList = list.possibleAnswers();
        List<String> expectedList = ReadFile.readLines(FOLDER_PATH + "eliminateWords3.txt");
        assertEquals(expectedList, actualList, "The number of words eliminated was not correct.");

        Collections.sort(actualList);
        Collections.sort(expectedList);

        for (int i = 0; i < actualList.size(); i++) {
            String actual = actualList.get(i);
            String expected = expectedList.get(i);
            assertEquals(expected, actual, "The remaining words after elimination were not the same.");
        }
    }

    @Test
    public void guessCorrectWordEliminateEmptyList() {
        WordleAnswer answer = new WordleAnswer("rocks");
        WordleWordList list = new WordleWordList();
        WordleWord feedback = answer.makeGuess("rocks");

        list.eliminateWords(feedback);
        List<String> possibleAnswers = list.possibleAnswers();
        assertTrue(possibleAnswers.isEmpty(), "Guessing the correct word didn't eliminate all answers.");
    }

    @Test
    public void guessCloseWordEliminateEmptyList() {
        WordleAnswer answer = new WordleAnswer("rocks");
        WordleWordList list = new WordleWordList();
        WordleWord feedback = answer.makeGuess("docks");

        list.eliminateWords(feedback);
        List<String> possibleAnswers = list.possibleAnswers();
        assertTrue(possibleAnswers.isEmpty());
    }

}
