package no.uib.inf102.wordle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf102.grid.GridCell;
import no.uib.inf102.grid.GridDimension;
import no.uib.inf102.wordle.model.GameState;
import no.uib.inf102.wordle.model.word.AnswerType;
import no.uib.inf102.wordle.model.word.WordleCharacter;

public class WordleView extends JPanel {

  private static final double OUTER_MARGIN = 20;
  private static final double INNER_MARGIN = 4;
  private static final double PREFERRED_SIDE_SIZE = 100;
  private static final int CELL_FONT_SIZE = (int) (PREFERRED_SIDE_SIZE / 1.5);
  private ViewableWordleModel model;
  private ColorTheme colors;

  public WordleView(ViewableWordleModel model) {
    this.model = model;
    this.colors = new DefaultColorTheme();
    this.setBackground(this.colors.getBackgroundColor());
    this.setFocusable(true);
    this.setPreferredSize(getDefaultSize(this.model.getDimension()));
  }

  private static Dimension getDefaultSize(GridDimension gd) {
    int width = (int) (PREFERRED_SIDE_SIZE * gd.cols() + INNER_MARGIN * (gd.cols() + 1) + 2 * OUTER_MARGIN);
    int height = (int) (PREFERRED_SIDE_SIZE * gd.rows() + INNER_MARGIN * (gd.cols() + 1) + 2 * OUTER_MARGIN);
    return new Dimension(width, height);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    drawGame(g2);
    if (this.model.getGameState() == GameState.GAME_OVER) {
      drawScreenText(g2, "GAME OVER");
    }
    if (this.model.getGameState() == GameState.VICTORY) {
      drawScreenText(g2, "VICTORY");
    }
  }

  private void drawScreenText(Graphics2D g2, String text) {
    g2.setColor(new Color(0, 0, 0, 128));
    g2.fill(this.getBounds());
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Monospaced", Font.BOLD, 50));
    Inf101Graphics.drawCenteredString(g2, text, getBounds());
  }

  private void drawGame(Graphics2D g2) {
    Rectangle2D box = new Rectangle2D.Double(
        OUTER_MARGIN,
        OUTER_MARGIN,
        this.getWidth() - OUTER_MARGIN * 2,
        this.getHeight() - OUTER_MARGIN * 2);

    g2.setColor(this.colors.getFrameColor());
    g2.fill(box);

    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
        box, this.model.getDimension(), INNER_MARGIN);
    drawCells(g2, this.model.getTilesOnBoard(), converter, this.colors);
    drawCells(g2, this.model.getCurrentGuess(), converter, this.colors);
  }

  private static void drawCells(Graphics2D g2, Iterable<GridCell<WordleCharacter>> cells,
      CellPositionToPixelConverter converter, ColorTheme colors) {
    g2.setFont(new Font("Monospaced", Font.PLAIN, CELL_FONT_SIZE));
    for (GridCell<WordleCharacter> cell : cells) {
      Rectangle2D box = converter.getBoundsForCell(cell.pos());
      char textChar = cell.value().letter;
      AnswerType colorChar = cell.value().answerType;

      String text = textChar + "";
      Color color = colors.getCellColor(colorChar);

      g2.setColor(color);
      g2.fill(box);
      g2.setColor(Color.BLACK);

      int middleX = (int) (box.getCenterX() - (PREFERRED_SIDE_SIZE / 5));
      int middleY = (int) (box.getCenterY() + (PREFERRED_SIDE_SIZE / 7.5));
      g2.drawString(text, middleX, middleY);
    }
  }
}
