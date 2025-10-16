package figures;

import exceptions.InvalidMoveException;

public class King extends Queen{
	public King(String color, char column, int row){
		super(color, column, row);
	}

	@Override
		public boolean moveTo(char x, int y) throws InvalidMoveException{
		int dx = Math.abs(x - column);
		int dy = Math.abs(y - row);
		if( dx <= 1 && dy <= 1 ) return true;
		throw new InvalidMoveException("Invalid move for King from " + column + row + " to " + x + y);
	}

	@Override
	public String toString(){
		return "King(color = " + color + ", Column = " + column + ", Row = " + row + ")";
	}
}
