package project.core.logic;

public class Cell {//extends  BoardEntity {
	
	private int row, col;
	private Pill pill;
	
	public Cell(int row, int col) {
		
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		
		return row;
	}
	
	public void setRow(int row) {
		
		this.row = row;
	}
	
	public int getCol() {
		
		return col;
	}
	
	public void setCol(int col) {
		
		this.col = col;
	}
	
	public boolean isEmpty() {
		
		return pill == null;
	}

	public void placePill(Pill pill) {
		
		this.pill = pill;
	}

	public Pill getPill() {

		return pill;
	}
}
