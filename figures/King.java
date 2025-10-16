package figures;

import exceptions.InvalidMoveException;

public class King extends Queen{
  public King(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public boolean moveTo(char x, int y) throws InvalidMoveException{
    int dx = Math.abs(x - posX);
    int dy = Math.abs(y - posY);
    return dx <= 1 && dy <= 1;
    throw new InvalidMoveException("Invalid move for King from " + posX + posY + " to " + x + y);
  }
}
