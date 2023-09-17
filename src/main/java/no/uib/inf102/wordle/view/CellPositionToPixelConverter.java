package no.uib.inf102.wordle.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf102.grid.CellPosition;
import no.uib.inf102.grid.GridDimension;

public class CellPositionToPixelConverter {
  private final Rectangle2D box;
  private final GridDimension gd;
  private final double margin;
  
  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }
  
  public Rectangle2D getBoundsForCell(CellPosition cellPosition) {
    double cellW = (box.getWidth() - margin * gd.cols() - margin) / gd.cols();
    double cellH = (box.getHeight() - margin * gd.rows() - margin) / gd.rows();
    double cellX = box.getX() + margin + (cellW + margin) * cellPosition.col();
    double cellY = box.getY() + margin + (cellH + margin) * cellPosition.row();
    return new Rectangle2D.Double(cellX, cellY, cellW, cellH);
  }
}

