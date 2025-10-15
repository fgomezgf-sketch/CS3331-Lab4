package chess.figures;

import chess.interfaces.IntChessBoard;
import chess.exceptions.InvalidCoordinateException;

/**
 * ChessBoard verifies integer coordinates 1..8 for X and Y.
 * Note: our pieces use char columns 'a'..'h'; convert via (col - 'a' + 1) in main when calling verifyCoordinate.
 */
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
