package project.core.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import project.core.gfx.GfxGame;
import project.core.logic.Board;
import project.core.search.TTEntry.Flag;

public class AlphaBeta {

	private Node root;
	private static final int MAX_ALPHA = -Integer.MAX_VALUE;
	private static final int MAX_BETA = Integer.MAX_VALUE;
	private static long MAX_TIME = 20000000000L;
	private static long timeToSpend = 5000000000L;
	public int alpha;
	public int beta;
	static int i = 0;
	
	static long lastTimeSpent = 0L;
	static long timeSpent = 0L;
	public AlphaBeta() {
		
		root = generateRoot();
		root.setHashKey(Zobrist.getInstance().getZobristHashing(root.getMyBoards()));
	}
	
	private Node generateRoot() {
		
		List<Board> boards = GfxGame.getInstance().getBoards();
		List<Integer> currBoard = new LinkedList<Integer>();

		for (Board board : boards) {
			for (int row = 0; row < Board.WIDTH; row++) {
				for (int col = 0; col < Board.HEIGHT; col++) {
					
					if (board.get(row, col).isEmpty())
						currBoard.add(0);
					
					else if (board.get(row, col).getPill().getColor()) 
						currBoard.add(1);
					
					else
						currBoard.add(-1);
				}
			}
		}
		
		return new Node(0, 0, currBoard, -1, false);
	}

	private Set<Node> generateOrderedMoves(Node node) {

		Set<Node> moves = new TreeSet<Node>();

		for (int cell = 0; cell < node.getMyBoards().size(); cell++) {
			if (node.getMyBoards().get(cell) ==  0) {
			
				List<Integer> currBoard = new LinkedList<Integer>(node.getMyBoards());
				currBoard.set(cell, node.isPlayer()? 1 : -1);
				
				moves.add(new Node(node.getHashKey(), node.getDepth(), currBoard, cell, !node.isPlayer()));
			}
		}
		return moves;
	}
	
	public int minimaxAlphabeta(int depth) {
		
		if (timeToSpend < MAX_TIME) {
			timeToSpend += 1000000000L; 
		}
		int searchDepth = 1;
		int ab = 0;
		long currTime = System.nanoTime();
		float timeIncrement = 0;
		
		while (depth >= searchDepth && timeSpent <= timeToSpend ) {
			ab = alphaBetaTT(root, searchDepth, MAX_ALPHA, MAX_BETA);
			searchDepth += 1;
			timeSpent = System.nanoTime() - currTime;
			timeIncrement = (float)(timeSpent - lastTimeSpent)/timeSpent;
			lastTimeSpent = timeSpent;
			timeSpent += timeIncrement*timeSpent; 
		}
		lastTimeSpent = 0L;
		timeSpent = 0L;

		return ab;
	}

//	--- The following two methods have been implemented just for the trials---

//	public int minimaxAlphabeta(int depth) {
//		
//		int searchDepth = 1;
//		int ab = 0;
//		long currTime = System.nanoTime();
//		while (depth >= searchDepth) {
//			
//			ab = plainMinimax(root, searchDepth, MAX_ALPHA, MAX_BETA);
//			searchDepth += 1;
//		}
//
//		return ab;
//	}
//	
//	public int plainMinimax (Node node, int depth, int alpha, int beta) {
//		i++;
//		int value;
//		if(depth == 0 || node.isTerminal()) {
//			value = node.getValue();
//			return value;
//		}
//		Set<Node> childrenOfNode = new TreeSet<Node>();
//		childrenOfNode = generateOrderedMoves(node);
//
//		if (!node.isPlayer()) {
//
//			value = MAX_ALPHA;
//			
//			for (Node child : childrenOfNode) {
//				
//				int abVal = plainMinimax(child, depth - 1, alpha, beta);
//				value = Math.max(value, abVal);
//				alpha = Math.max(alpha, value);
//
//				if (beta <= alpha)
//					break; // β cut-off
//			}
//		}
//		
//		else {
//			value = MAX_BETA;
//	
//			for (Node child : childrenOfNode) {
//
//				value = Math.min(value, plainMinimax(child, depth - 1, alpha, beta));
//				beta = Math.min(beta, value);
//				
//				if (beta <= alpha)
//					break; //α cut-off
//				
//			}
//		}
//
//		return value;
//	}
	
	public int alphaBetaTT(Node node, int depth, int alpha, int beta) {
		i++;
		int alphaOrigin = alpha;
		int value;
		TranspositionTable table = TranspositionTable.getInstance();
		TTEntry ttEntry = table.getTTEntry(node.getHashKey());

		if(ttEntry != null && ttEntry.getDepth() >= depth) {
			
			if(ttEntry.getType() == Flag.EXACT_VALUE) // stored value is exact
				return ttEntry.getValue();
	 
			if(ttEntry.getType() == Flag.LOWERBOUND && ttEntry.getValue() > alpha)
				alpha = ttEntry.getValue(); // update lowerbound alpha if needed
	 
			else if(ttEntry.getType() == Flag.UPPERBOUND && ttEntry.getValue() < beta)
				beta = ttEntry.getValue(); // update upperbound beta if needed
	 
			if(alpha >= beta)
				return ttEntry.getValue(); // if lowerbound surpasses upperbound
		}
	 
		if(depth == 0 || node.isTerminal()) {
			
			value = node.getValue();
			
			if(value <= alpha) // a lowerbound value
				table.storeTTEntry(node.getHashKey(), Flag.LOWERBOUND, value, depth);
	 
			else if(value >= beta) // an upperbound value
				table.storeTTEntry(node.getHashKey(), Flag.UPPERBOUND, value, depth);
	 
			else // a true minimax value
				table.storeTTEntry(node.getHashKey(), Flag.EXACT_VALUE, value, depth);
			
			return value;
		}
		
	 
		Set<Node> childrenOfNode = new TreeSet<Node>();
		childrenOfNode = generateOrderedMoves(node);

		if (!node.isPlayer()) {

			value = MAX_ALPHA;
			
			for (Node child : childrenOfNode) {
				
				int abVal = alphaBetaTT(child, depth - 1, alpha, beta);
				value = Math.max(value, abVal);
				alpha = Math.max(alpha, value);

				if (beta <= alpha)
					break; // β cut-off
			}
		}
		
		else {
			value = MAX_BETA;
	
			for (Node child : childrenOfNode) {

				value = Math.min(value, alphaBetaTT(child, depth - 1, alpha, beta));
				beta = Math.min(beta, value);

				if (beta <= alpha)
					break; //α cut-off
				
			}
			
		}
		
		if (value <= alphaOrigin) // a lowerbound value
			 table.storeTTEntry(node.getHashKey(), Flag.LOWERBOUND, value, depth);
		
		else if (value >= beta) // an upperbound value
			 table.storeTTEntry(node.getHashKey(), Flag.UPPERBOUND, value, depth);
		
		else // a true minimax value
			 table.storeTTEntry(node.getHashKey(), Flag.EXACT_VALUE, value, depth);
		
		return value;
	}
	
	public int selectMove() {
		
		Node bestMove = null;
		int value = MAX_ALPHA;
		
		for (Node child : generateOrderedMoves(root)) {
			
			TTEntry entry = TranspositionTable.getInstance().getTTEntry(child.getHashKey());
			
			if (entry != null && value < entry.getValue()) {
				
				value = entry.getValue();
				bestMove = child;
			}
		}
		
		return bestMove.getPosition();
	}
}
