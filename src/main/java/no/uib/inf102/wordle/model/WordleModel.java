package no.uib.inf102.wordle.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf102.grid.CellPosition;
import no.uib.inf102.grid.GridCell;
import no.uib.inf102.grid.GridDimension;
import no.uib.inf102.wordle.controller.ControllableWordleModel;
import no.uib.inf102.wordle.model.word.WordleCharacter;
import no.uib.inf102.wordle.model.word.AnswerType;
import no.uib.inf102.wordle.model.word.WordleAnswer;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.resources.GetWords;
import no.uib.inf102.wordle.view.ViewableWordleModel;

public class WordleModel implements ViewableWordleModel, ControllableWordleModel {

    /**
     * Wordle board. Whenever a guess is locked in the board is given the acompanying letters and answer types (CORRECT, WRONG_POSITION, WRONG)
     */
    private WordleBoard board;
    /**
     * The word that the user needs to guess.
     */
    private WordleAnswer answer;
    /**
     * Current  Wordle guess. 
     */
    private String currentGuess;

    private GameState gameState;

    public WordleModel(WordleBoard board) {
        this.board = board;
        this.answer = new WordleAnswer();
        this.currentGuess = "";

        this.gameState = GameState.ACTIVE_GAME;
    }

    @Override
    public boolean removeCharacter() {
    	if(currentGuess.isEmpty())
    		return false;

    	currentGuess = currentGuess.substring(0, currentGuess.length()-1);
        return true;
    }

    @Override
    public boolean addCharacter(char c) {
    	if(currentGuess.length()>=GetWords.WORD_LENGTH)
    		return false;
    	currentGuess = currentGuess+c;
        return true;
    }

    @Override
    public WordleWord makeGuess() throws IllegalArgumentException {
        if (!GetWords.isLegalGuess(currentGuess))
            throw new IllegalArgumentException("Word is not legal");
    
        // Check what letters were CORRECT/WRONG POSITION/WRONG
        WordleWord guessFeedback = answer.makeGuess(currentGuess);
        board.setRow(guessFeedback);
        if (guessFeedback.allMatch())
            gameState = GameState.VICTORY;
        else if (board.getCurrentRow()+1 > board.rows())
            gameState = GameState.GAME_OVER;

        currentGuess = "";
        return guessFeedback;
    }

    @Override
    public GridDimension getDimension() {
        return board;
    }

    @Override
    public Iterable<GridCell<WordleCharacter>> getTilesOnBoard() {
        return board;
    }

    @Override
    public Iterable<GridCell<WordleCharacter>> getCurrentGuess() {
        List<GridCell<WordleCharacter>> cellList = new ArrayList<>();
        int col = 0;
        for (Character c : currentGuess.toCharArray()) {
        	WordleCharacter cg = new WordleCharacter(c, AnswerType.BLANK);
            CellPosition pos = new CellPosition(board.getCurrentRow(), col++);
            cellList.add(new GridCell<WordleCharacter>(pos, cg));
        }
        return cellList;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getTimerDelay() {
        return 1000;
    }

    @Override
    public void clockTick() {
        if (gameState == GameState.GAME_OVER)
            return;
    }

    @Override
    public void reset() {
        this.answer = new WordleAnswer();
        this.currentGuess = "";
        this.board = new WordleBoard(this.board.rows(), this.board.cols());

        this.gameState = GameState.ACTIVE_GAME;
    }

}
