package no.uib.inf102.wordle.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf102.wordle.model.GameState;
import no.uib.inf102.wordle.view.WordleView;


public class WordleHumanController implements KeyListener {

    private ControllableWordleModel model;
    private WordleView view;

    public WordleHumanController(ControllableWordleModel model, WordleView view) {
        this.model = model;
        this.view = view;

        view.addKeyListener(this);
        view.setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (model.getGameState() != GameState.ACTIVE_GAME)
            return;

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_SPACE)
            model.removeCharacter();
        else if (keyCode == KeyEvent.VK_ENTER) {
            try {
                model.makeGuess();
            } catch (IllegalArgumentException ex) {
                // Do nothing if invalid guess
            } 
        } 
        // Alphabet characters
        else if (keyCode >= 65 && keyCode <= 90)
            model.addCharacter((char) Character.toLowerCase(keyCode));
        // Hit 1 to reset game
        else if (e.getKeyCode() == KeyEvent.VK_1) {
            model.reset();
        }
        
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
