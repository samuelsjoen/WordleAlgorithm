package no.uib.inf102.grid;

import java.util.Objects;

public class CellPosition {
    
    public final int row;
    public final int col;

    public CellPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public CellPosition shiftedBy(GridDirection dir) {
        return new CellPosition(row+dir.dx, col+dir.dy);
    }

    public GridDirection getDirectionTowards(CellPosition to) {
		int fromX = this.col();
		int fromY = this.row();

		int toX = to.col();
		int toY = to.row();

		if (fromX < toX)
			return GridDirection.EAST;
        else if (fromY < toY)
			return GridDirection.SOUTH;
		else if (fromX > toX)
			return GridDirection.WEST;
		
		else
			return GridDirection.NORTH;
	}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CellPosition))
            return false;

        CellPosition other = (CellPosition) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

}
