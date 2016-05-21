package project.core.search;

import java.util.List;

public class Node implements Comparable<Node> {
	
	private int depth;
	private int value;
	private List<Integer> myBoards;
	private boolean player;
	private boolean isTerminal;
	private int position;
	private long hashKey;
	
	public Node(long fatherHash, int depth, List<Integer> myBoards, int position, boolean player) {
		
		this.hashKey = Zobrist.getInstance().getZobFromFather(fatherHash, position, (player? 0 : 1));
		this.depth = depth + 1;
		this.myBoards = myBoards; 
		this.player = player;
		this.setPosition(position);
		
		if (position > -1)
			value = EvaluationFunction.getInstance().evaluate(this);
	}

	public boolean isTerminal() {
		
		return isTerminal;
	}
	
	public void setIsTerminal(boolean isTerminal) {
		
		this.isTerminal = isTerminal;
	}

	public int getValue() {
		
		return this.value;
	}

	public List<Integer> getMyBoards() {
		
		return myBoards;
	}

	public void setMyBoards(List<Integer> myBoards) {
		
		this.myBoards = myBoards;
	}

	public boolean isPlayer() {
		
		return player;
	}

	public void setPlayer(boolean player) {
		
		this.player = player;
	}

	public void setValue(int value) {
		
		this.value = value;
	}

	public int getDepth() {
		
		return this.depth;
	}

	public int getPosition() {
		
		return position;
	}

	public void setPosition(int position) {
		
		this.position = position;
	}
	
	public long getHashKey() {
		
		return this.hashKey;
	}

	public void setHashKey(long hashKey) {
		
		this.hashKey = hashKey;
	}

	@Override
	public int compareTo(Node node) {
		
		TranspositionTable table = TranspositionTable.getInstance();
		
		if (table.getTTEntry(hashKey) != null && table.getTTEntry(node.getHashKey()) != null){
			
			if (value > node.getValue())
				return 1;
			
			if (value < node.getValue())
				return -1;
			
			return 0;
		}

		return 1;
	}
}
