package project.core.search;

public class TTEntry {

	public static enum Flag {EXACT_VALUE , LOWERBOUND, UPPERBOUND};
	private int value;
	private int depth;
	private Flag type;
	
	public TTEntry(Flag type, int value, int depth) {
		
		this.type = type;
		this.value = value;
		this.depth = depth;
	}

	public Flag getType() {
		
		return type;
	}

	public void setType(Flag type) {
		
		this.type = type;
	}

	public int getValue() {
		
		return value;
	}

	public void setValue(int value) {
		
		this.value = value;
	}

	public int getDepth() {
		
		return depth;
	}

	public void setDepth(int depth) {
		
		this.depth = depth;
	}
}
