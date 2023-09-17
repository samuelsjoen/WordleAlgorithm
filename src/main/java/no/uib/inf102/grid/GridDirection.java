package no.uib.inf102.grid;

import java.util.Random;

/**
 * This class represents the 8 different directions
 * (plus the direction of no movement) in a grid
 * which one can move by changing either x or y coordinate by at most 1
 */
public enum GridDirection {
	EAST(1, 0),
	NORTH(0, -1),
	WEST(-1, 0),
	SOUTH(0, 1);

	public final int dy;
    public final int dx;

	private static Random random = new Random();

	private GridDirection(int dy, int dx) {
		this.dy = dy;
        this.dx = dx;
	}

	public static GridDirection randomDirection() {
		int randIndex = random.nextInt(GridDirection.values().length);
		return GridDirection.values()[randIndex];
	}

}