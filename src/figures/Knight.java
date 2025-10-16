package figures;

import exceptions.InvalidMoveException;

public class Knight extends Figure {
    public Knight(String color, char column, int row) {
        super(color, column, row);
    }

    @Override
    public boolean moveTo(char toX, int toY) throws InvalidMoveException {
        int dx = Math.abs(toX - this.column);
        int dy = Math.abs(toY - this.row);
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) return true;
        throw new InvalidMoveException("Invalid move for knight from " + column + row + " to " + toX + toY);
    }

    @Override
    public String toString() {
        return "Knight{color='" + color + "', column=" + column + ", row=" + row + "}";
    }
}

