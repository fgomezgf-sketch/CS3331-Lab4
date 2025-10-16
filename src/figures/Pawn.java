package figures;

import exceptions.InvalidMoveException;

public class Pawn extends Figure{
	public Pawn(String color, char column, int row){
		super(color, column, row);
	}

	@Override
	public boolean moveTo(char x, int y) throws InvalidMoveException{
		int dy = y - row;
		if((color.equalsIgnoreCase("white") && dy == 1) || (color.equalsIgnoreCase("black") && dy == -1)) return true;
		throw new InvalidMoveException("Invalid move for Pawn from " + column + row + " to " + x + y);
	}

	@Override
	public String toString(){
		return "Pawn(color = " + color + ", Column = " + column + ", Row = " + row + ")";
	}
}
    
