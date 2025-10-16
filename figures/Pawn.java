package figures;
public class Pawn extends Figure{
  public Pawn(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public boolean moveTo(char x, int y) throws InvalidMoveException{
    int dy = y - posY;
    return(color.equalsIgnoreCase("white") && dy == 1) || (color.equalsIgnoreCase("black") && dy == -1);
    throw new InvalidMoveException("Invalid move for Pawn from " + posX + posY + " to " + x + y);
  }
}
    
