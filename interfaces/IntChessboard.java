package chess.interfaces;

import chess.exceptions.InvalidCoordinateException;

public interface IntChessBoard {
    /**
     * Verify coordinates are inside the chessboard.
     * X is 1..8 (column index), Y is 1..8 (row).
     * Throws InvalidCoordinateException if not valid.
     */
    boolean verifyCoordinate(int X, int Y) throws InvalidCoordinateException;
}

