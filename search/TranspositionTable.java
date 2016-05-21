package project.core.search;

import java.util.HashMap;
import java.util.Map;

import project.core.search.TTEntry.Flag;

public class TranspositionTable {
	
	private static TranspositionTable instance;
	public Map<Long, TTEntry> table = new HashMap<Long, TTEntry>();
	
	protected TranspositionTable() {
	
	}
		
	public static TranspositionTable getInstance() {
		
		if (instance == null)
			instance = new TranspositionTable();
		
		return instance;
	}
	
	protected void storeTTEntry(long hashKey, Flag type, int value, int depth) {
		
		TTEntry ttEntry = new TTEntry(type, value, depth);
		table.put(hashKey, ttEntry);
	}
	
	public TTEntry getTTEntry(long hashKey) {
		
		return table.get(hashKey);
	}
	
	public int size() {
		
		return table.size();
	}
}
