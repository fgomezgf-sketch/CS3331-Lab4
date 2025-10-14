package chess.figures;

import chess.exceptions.InvalidMoveException;

public class Knight extends Figure {
    public Knight(String color, char column, int row) {
        super(color, column, row);
    }

    @Override
    public boolean moveTo(char toX, int toY) throws InvalidMoveException {
        if (toX < 'a' || toX > 'h' || toY < 1 || toY > 8)
            throw new InvalidMoveException("Target out of board range.");

        int dx = Math.abs(toX - this.column);
        int dy = Math.abs(toY - this.row);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public String toString() {
        return "Knight{color='" + color + "', column=" + column + ", row=" + row + "}";
    }
}

