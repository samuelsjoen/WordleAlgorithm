package no.uib.inf102.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid<E> implements IGrid<E> {

    private int cols;
    private int rows;
    protected List<List<E>> grid;

    public Grid(int rows, int cols) {
        this(rows, cols, null);
    }

    public Grid(int rows, int cols, E defaultValue) {
        this.rows = rows;
        this.cols = cols;
        grid = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            List<E> rowList = new ArrayList<>();
            for (int col = 0; col < cols; col++) {
                rowList.add(defaultValue);
            }
            grid.add(rowList);
        }
    }
    

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public Iterator<GridCell<E>> iterator() {
        List<GridCell<E>> list = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                CellPosition pos = new CellPosition(row, col);
                list.add(new GridCell<E>(pos, get(pos)));
            }
        }
        return list.iterator();
    }

    @Override
    public void set(CellPosition pos, E value) {
        this.grid.get(pos.row).set(pos.col, value);
    }

    @Override
    public E get(CellPosition pos) {
        return this.grid.get(pos.row).get(pos.col);
    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        if (pos.row < 0 || pos.row >= rows)
            return false;
        if (pos.col < 0 || pos.col >= cols)
            return false;
        return true;
    }
    
    public void printGrid() {
        for (List<E> list : grid) {
            System.out.println(list);
        }
    }
}
