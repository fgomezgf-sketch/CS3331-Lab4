package figures;

import interfaces.IntBishop;
import exceptions.InvalidMoveException;

/**
 * Bishop implements IntBishop (NOT IntFigure / Figure)
 * Therefore Bishop objects cannot be stored in the Figure[] array.
 */
public class Bishop implements IntBishop {
    private String color;
    private char column;
    private int row;

    public Bishop(String color, char column, int row) {
        this.color = color;
        this.column = column;
        this.row = row;
    }

    public String getColor() { return color; }
    public char getColumn() { return column; }
    public int getRow() { return row; }

    @Override
    public boolean moveToBishop(char toX, int toY) throws InvalidMoveException {
        if (toX < 'a' || toX > 'h' || toY < 1 || toY > 8)
            throw new InvalidMoveException("Target out of board range.");

        int dx = Math.abs(toX - this.column);
        int dy = Math.abs(toY - this.row);
        return dx == dy;
    }

    @Override
    public String toString() {
        return "Bishop{" + "color='" + color + '\'' + ", column=" + column + ", row=" + row + '}';
    }
}

