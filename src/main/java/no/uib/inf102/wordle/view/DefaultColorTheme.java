package no.uib.inf102.wordle.view;

import java.awt.Color;

import no.uib.inf102.wordle.model.word.AnswerType;

public class DefaultColorTheme implements ColorTheme {
  
  @Override
  public Color getCellColor(AnswerType ansType) {
    Color color = switch(ansType) {
      case BLANK -> Color.WHITE;
      case WRONG -> Color.GRAY;
      case WRONG_POSITION -> Color.YELLOW;
      case CORRECT -> Color.GREEN;
      default -> throw new IllegalArgumentException(
        "No available color for '" + ansType + "'");
    };
    return color;
  }

  @Override
  public Color getFrameColor() {
    return new Color(0, 0, 0, 0);
  }

  @Override
  public Color getBackgroundColor() {
    return null;
  }
  
}
