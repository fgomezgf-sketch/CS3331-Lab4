package figures;

import interfaces.IntChessboard;
import exceptions.InvalidCoordinateException;

public class Chessboard implements IntChessboard {
    private static final int MINROW = 1;
    private static final int MAXROW = 8;
    private static final char MINCOLUMN = 'a';
    private static final char MAXCOLUMN = 'h';

    @Override
    public boolean verifyCoordinate(char X, int Y) throws InvalidCoordinateException {
        if (X < MINCOLUMN || X > MAXCOLUMN || Y < MINROW || Y > MAXROW) {
            throw new InvalidCoordinateException("Coordinate (" + X + "," + Y + ") is outside the board.");
        }
        return true;
    }
}
