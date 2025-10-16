package figures;

import exceptions.InvalidMoveException;

public class Rook extends Figure{
	public Rook(String color, char column, int row){
		super(color, column, row);
	}

	@Override
	public boolean moveTo(char x, int y) throws InvalidMoveException{
		if(x == column || y == row) return true;
		throw new InvalidMoveException("Invalid move for Rook from " + column + row + " to " + x + y);
	}

	@Override
	public String toString(){
		return "Rook(color = " + color + ", Column = " + column + ", Row = " + row + ")";
	}
}
