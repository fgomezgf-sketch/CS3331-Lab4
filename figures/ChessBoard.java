package figures;

import interfaces.IntChessBoard;
import exceptions.InvalidCoordinateException;

public class ChessBoard implements IntChessBoard {
    private static final int MIN = 1;
    private static final int MAX = 8;

    @Override
    public boolean verifyCoordinate(int X, int Y) throws InvalidCoordinateException {
        if (X < MIN || X > MAX || Y < MIN || Y > MAX) {
            throw new InvalidCoordinateException("Coordinate (" + X + "," + Y + ") is outside the board.");
        }
        return true;
    }
}
