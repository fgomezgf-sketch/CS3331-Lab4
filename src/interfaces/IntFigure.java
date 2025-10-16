package interfaces;

import exceptions.InvalidMoveException;

public interface IntFigure {
  
    boolean moveTo(char toX, int toY) throws InvalidMoveException;
}

