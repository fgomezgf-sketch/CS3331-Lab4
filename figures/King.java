package figures;
public class King extends Queen{
  public King(String color, char posX, int posY){
    super(color, posX, posY);
  }

  @Override
  public boolean moveTo(char x, int y){
    int dx = Math.abs(x - posX);
    int dy = Math.abs(y - posY);
    return dx <= 1 && dy <= 1;
  }
}
