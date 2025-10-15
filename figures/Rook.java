package figures;
public class Rook extends Figure{
  public Rook(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public boolean moveTo(char x, int y){
    return(x == posX || y == posY);
  }
}
