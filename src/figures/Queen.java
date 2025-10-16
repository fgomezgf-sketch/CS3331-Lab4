package figures;
import interfaces.IntBishop;
import exceptions.InvalidMoveException;

public class Queen extends Rook implements IntBishop{
	public Queen(String color, char column, int row){
		super(color, column, row);
	}

	@Override
	public boolean moveToBishop(char x, int y) throws InvalidMoveException{
		if(Math.abs(x - column) == Math.abs(y - row)) return true;
		throw new InvalidMoveException("Invalid move for Queen from " + column + row + " to " + x + y);
	}

	@Override
	public String toString(){
		return "Queen(color = " + color + ", Column = " + column + ", Row = " + row + ")";
	}
}
