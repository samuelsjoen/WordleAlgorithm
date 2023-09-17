package no.uib.inf102.wordle.model.word;

/**
 * This enum represents the answer type a WordleCharacter can have. The possible
 * answer types are BLANK, WRONG, WRONG_POSITION and CORRECT.
 */
public enum AnswerType {

    BLANK('b'),
    WRONG('w'),
    WRONG_POSITION('p'),
    CORRECT('c');

    public final char character;

    private AnswerType(char c) {
        this.character = c;
    }

}
