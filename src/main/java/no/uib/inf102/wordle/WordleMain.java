package no.uib.inf102.wordle;

import javax.swing.*;

import no.uib.inf102.wordle.controller.WordleAIController;
import no.uib.inf102.wordle.controller.WordleHumanController;
import no.uib.inf102.wordle.model.WordleBoard;
import no.uib.inf102.wordle.model.WordleModel;
import no.uib.inf102.wordle.view.WordleView;

public class WordleMain {

  public static final String WINDOW_TITLE = "INF102 Wordle";

  public static void main(String[] args) {
    WordleBoard board = new WordleBoard(8, 5);
    WordleModel model = new WordleModel(board);
    WordleView view = new WordleView(model);
    // new WordleHumanController(model, view);
    new WordleAIController(model, view);

    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setContentPane(view);

    frame.pack();
    frame.setVisible(true);
  }

}
