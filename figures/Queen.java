package figures;
import interfaces.IntBishop;
import exceptions.InvalidMoveException;

public class Queen extends Rook implements IntBishop{
  public Queen(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public Boolean moveToBishop(char x, int y) throws InvalidMoveException{
    if(Math.abs(x - posX) == Math.abs(y - posY)) return true;
    return false;
  }

  @Override
  public 
