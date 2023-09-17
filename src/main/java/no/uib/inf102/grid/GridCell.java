package no.uib.inf102.grid;

import java.util.Objects;

public class GridCell<E> {
 
    private CellPosition pos;
    private E value;

    public GridCell(CellPosition pos, E value) {
        this.pos = pos;
        this.value = value;
    }

    public CellPosition pos() {
        return pos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GridCell)) {
            return false;
        }
        GridCell<E> gridCell = (GridCell<E>) o;
        return Objects.equals(pos, gridCell.pos) && Objects.equals(value, gridCell.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, value);
    }

    public E value() {
        return value;
    }
}
