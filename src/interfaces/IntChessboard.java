package interfaces;

import exceptions.InvalidCoordinateException;

public interface IntChessboard {
    /**
     * Verify coordinates are inside the chessboard.
     * X is 1..8 (column index), Y is 1..8 (row).
     * Throws InvalidCoordinateException if not valid.
     */
    boolean verifyCoordinate(char X, int Y) throws InvalidCoordinateException;
}

