package project.core.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Game {
	
	private static Game instance;
	private RealPlayer player;
	private PCPlayer pc;
	private List<Board> boards = new ArrayList<Board>();
	private boolean isPlayerTurn;
	public static final int BOARDS = 4;

	protected Game() {
		
	}
	
	public static Game getInstance() {
		
		if (instance == null)
			instance = new Game();
		
		return instance;
	}
	
	public void createGame() {
		
		isPlayerTurn = true;
		player = new RealPlayer();
		pc = new PCPlayer();
		Board board = null;
		
		for (int i = 0; i < BOARDS; i++) {
			board = new Board();
			board.initBoard(getRealPlayer(), getPcPlayer());
			boards.add(board);
		}
	}
	
	public RealPlayer getRealPlayer() {
		
		return player;
	}
	
	protected void setRealPlayer(RealPlayer player) {
		
		this.player = player;
	}

	public PCPlayer getPcPlayer() {
		
		return pc;
	}
	
	protected void setPcPlayer(PCPlayer pc) {
		
		this.pc = pc;
	}

	public List<Board> getBoards() {
		
		return boards;
	}
	
	public Board getBoard(int i) {
		
		return boards.get(i);
	}
	
	protected void setBoard(Board board) {
		
		boards.add(board);
	}

	public boolean placePill(int row, int col, Board board) {
		
		if (isMoveLegal(row, col, board)) {
		
			Player currentPlayer = isPlayerTurn ? player : pc;
			Pill pill = isPlayerTurn ? new Pill(true) : new Pill(false);
			board.get(row, col).placePill(pill);
			currentPlayer.addPill(pill);
			isPlayerTurn = !isPlayerTurn;
			return isAwin(row, col, board);
		}
		
		return false;
	}

	public boolean isMoveLegal(int row, int col, Board board) {
		
		if (board.isOutOfBoard(row, col))
			return false;
		
		if (board.isOccupied(row, col))
			return false;
		
		return true;
	}

	public boolean isPlayerTurn() {
		
		return isPlayerTurn;
	}

	public void setPlayerTurn(boolean isPlayerTurn) {
		
		this.isPlayerTurn = isPlayerTurn;
	}
	
	public boolean isAwin(int row, int col, Board board) {
		
		boolean win = winningRow(row, col, board) || winningCol(row, col, board) || 
				winningDiagonal(row, col, board) || winningVertical(row, col, board) ||
				winning3DDiagonal(row, col, board) || winning3DCol(row, col, board) || winning3DRow(row, col, board);
		return win;
	}

	private boolean winningRow(int row, int col, Board board) { 
		
		boolean color = board.get(row, col).getPill().getColor();
		boolean hope = true;
		
		for (int i = 0; i < 4 && hope; i++) 						
			
			hope = board.get(row, i).getPill() != null && board.get(row, i).getPill().getColor() == color;

		return hope;
	}

	private boolean winningCol(int row, int col, Board board) {
		
		boolean color = board.get(row, col).getPill().getColor();
		boolean hope = true;
		
		for (int i = 0; i < 4 && hope; i++)
			hope = board.get(i, col).getPill() != null && board.get(i, col).getPill().getColor() == color;

		return hope;
	}

	private boolean winningDiagonal(int row, int col, Board board) {

		boolean hope = false;
		boolean color = board.get(row, col).getPill().getColor();
		
		if (row == col) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = board.get(i, i).getPill() != null && board.get(i, i).getPill().getColor() == color;
		}
		else if (col == Board.HEIGHT - row - 1) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = board.get(Board.HEIGHT-i-1, i).getPill() != null && 
					board.get(Board.HEIGHT-i-1, i).getPill().getColor() == color;
			
		}
		return hope;
	}

	private boolean winningVertical(int row, int col, Board board) {
		
		boolean color = board.get(row, col).getPill().getColor();
		boolean hope = true;

		for (int i = 0; i < BOARDS && hope; i++)

			hope = boards.get(i).get(row, col).getPill() != null && boards.get(i).get(row, col).getPill().getColor() == color;

		return hope;
	}

	private boolean winning3DDiagonal(int row, int col, Board board) {
		
		boolean color = board.get(row, col).getPill().getColor();
		boolean hope = false;
		int boardId = boards.indexOf(board);
		
		if (row == col && col == boardId) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(i).get(i, i).getPill() != null && boards.get(i).get(i, i).getPill().getColor() == color;
		}
		else if (row == col && boardId == Board.HEIGHT - row -1) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(Board.HEIGHT - i - 1).get(i, i).getPill() != null && 
					boards.get(Board.HEIGHT - i - 1).get(i, i).getPill().getColor() == color;
			
		}
		else if (boardId == col && col == Board.HEIGHT - row - 1) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(Board.HEIGHT - i - 1).get(i, Board.HEIGHT - 1 - i).getPill() != null && 
					boards.get(Board.HEIGHT - 1 - i).get(i, Board.HEIGHT - 1 - i).getPill().getColor() == color;
			
		}
		else if (col == Board.HEIGHT - row - 1 && boardId == row) {
		
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(i).get(i, Board.HEIGHT - 1 - i).getPill() != null && 
						boards.get(i).get(i, Board.HEIGHT - 1 - i).getPill().getColor() == color;
				
		}

		return hope;
	}
	
	private boolean winning3DCol(int row, int col, Board board) {
		
		boolean color = board.get(row, col).getPill().getColor();
		boolean hope = false;
		int boardId = boards.indexOf(board);
		
		if (row == boardId) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(i).get(i, col).getPill() != null && 
					boards.get(i).get(i, col).getPill().getColor() == color;
		}
		else if (boardId == Board.HEIGHT - row - 1 ) {

			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(Board.HEIGHT - 1 - i).get(i, col).getPill() != null && 
					boards.get(Board.HEIGHT - 1 - i).get(i, col).getPill().getColor() == color;
		}

		return hope;
	}
	
	private boolean winning3DRow(int row, int col, Board board) {
		
		boolean color = board.get(row, col).getPill().getColor();
		boolean hope = false;
		int boardId = boards.indexOf(board);
		
		if (boardId == col) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(i).get(row, i).getPill() != null && 
					boards.get(i).get(row, i).getPill().getColor() == color;
		}
		else if (boardId == Board.HEIGHT - col - 1) {
			
			hope = true;
			for (int i = 0; i < 4 && hope; i++)
				hope = boards.get(Board.HEIGHT - 1 -i).get(row, i).getPill() != null && 
					boards.get(Board.HEIGHT - 1 -i).get(row, i).getPill().getColor() == color;
		}

		return hope;
	}

	public List<Integer[]> getMoves(boolean player)
	{
		List<Integer[]> moves = new LinkedList<Integer[]>();
		
		for ( int i = 0; i < boards.size(); i++) {
			for (int row = 0; row < Board.HEIGHT; row++) {
				for (int col = 0; col < Board.WIDTH; col++) {
					
					if (boards.get(i).get(row, col).isEmpty()) {
						Integer[] move = new Integer[3];
						move[0] = i;
						move[1] = row;
						move[2] = col;
						moves.add(move);
					}
				}
			}
		}
		
		return moves;
	}
}
