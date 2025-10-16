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
        int dx = Math.abs(toX - this.column);
        int dy = Math.abs(toY - this.row);
        if(dx == dy) return true;
        throw new InvalidMoveException("Invalid move for knight from " + column + row + " to " + toX + toY);
    }

    @Override
    public String toString() {
        return "Bishop{" + "color='" + color + '\'' + ", column=" + column + ", row=" + row + '}';
    }
}

