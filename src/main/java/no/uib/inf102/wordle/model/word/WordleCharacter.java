package no.uib.inf102.wordle.model.word;

import java.util.Objects;

/**
 * This class represents a single Wordle character, and is comprised of a
 * Character and an AnswerType.
 */
public class WordleCharacter {

	public final Character letter;
	public final AnswerType answerType;

	public WordleCharacter(Character letter, AnswerType answerType) {
		this.letter = letter;
		this.answerType = answerType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(answerType, letter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordleCharacter other = (WordleCharacter) obj;
		return answerType == other.answerType && Objects.equals(letter, other.letter);
	}

}
