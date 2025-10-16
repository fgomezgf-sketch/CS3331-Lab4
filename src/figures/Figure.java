package figures;

import interfaces.IntFigure;


public abstract class Figure implements IntFigure {
    protected String color; 
    protected char column;  
    protected int row;     

    public Figure() {}

    public Figure(String color, char column, int row) {
        this.color = color;
        this.column = column;
        this.row = row;
    }

    public String getColor() { return color; }
    public char getColumn() { return column; }
    public int getRow() { return row; }

    public void setColumn(char column) { this.column = column; }
    public void setRow(int row) { this.row = row; }

    @Override
    public String toString() {
        return "";
    }
}

