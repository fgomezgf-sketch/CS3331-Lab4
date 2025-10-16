package figures;
public class Rook extends Figure{
  public Rook(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public boolean moveTo(char x, int y) throws InvalidMoveException{
    return(x == posX || y == posY);
    throw new InvalidMoveException("Invalid move for Rook from " + posX + posY + " to " + x + y);
  }

  @Override
  public String toString(){
    return "Rook(color = " + color + ", Column = " + posX + ", Row = " + posY + ")";
  }
}
