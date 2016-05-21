package project.core.logic;

public class Board {
	
	public static final int HEIGHT = 4;
	public static final int WIDTH = 4;
	private Cell[][] board;
	
	public Board() {
		
		board = new Cell[HEIGHT][WIDTH];
	}
	
	public Cell get(int row, int col) {
		
		return board[row][col];
	}
	
	public void set(int row, int col, Cell cell) {
		
		board[row][col] = cell;
	}

	public boolean isOutOfBoard(int row, int col) {
		
		return row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH;
	}

	public boolean isOccupied(int row, int col) {

		return !board[row][col].isEmpty();
	}

	public void initBoard(RealPlayer player, PCPlayer pc) {
		
		for (int row = 0; row < Board.HEIGHT; row++)
			for (int col = 0; col < Board.WIDTH; col++)
				board[row][col] = new Cell(row, col);
						
	}

	public boolean isLegalClick(pythagoras.i.IPoint coords)	{
		
		return coords.y() >= 0 && coords.y() < HEIGHT && coords.x() >= 0 && coords.x() < WIDTH;
	}
}
