package project.core.search;

import java.util.List;
import java.util.Random;


public class Zobrist {
	
	private static final int PC = 0;
    private static final int PLAYER = 1;
    private static Zobrist instance;

    private long[][] randomArray = new long[64][2];
    
 	protected Zobrist() {

        	for (int i = 0; i < 64; i++)
        		for (int j = 0; j < 2; j++)
        			randomArray[i][j] = new Random().nextLong();
	}

 	public static Zobrist getInstance() {
 		
 		if (instance == null)
			instance = new Zobrist();
		
		return instance;
 	}
	
	public long getZobristHashing(List<Integer> boards) {
		
		long hash = 0;
				    
		//XOR each occupied location
		for (int cell = 0; cell < boards.size(); cell++)
			
			if (boards.get(cell) != 0){
				int j = boards.get(cell) == 1 ? PLAYER : PC;
				hash ^= randomArray[cell][j];
			}
		return hash;
	}
	
	public long getZobFromFather(long fatherHashKey, int cell, int j) {
		
		if (cell == -1)
			return fatherHashKey;
		
		return fatherHashKey ^ randomArray[cell][j];
	}
}
