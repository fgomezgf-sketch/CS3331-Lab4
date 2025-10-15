package figures;
public class Pawn extends Figure{
  public Pawn(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public boolean moveTo(char x, int y) throws Exception{
    int dy = y - posY;
    return(color.equalsIgnoreCase("white") && dy == 1) || (color.equalsIgnoreCase("black") && dy == -1);
  }
}
    
