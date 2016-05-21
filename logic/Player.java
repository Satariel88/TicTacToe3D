package project.core.logic;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {
	
	private List<Pill> pills;
	
	public Player() {
		
		this.pills = new LinkedList<Pill>();
	}
	
	public void addPill(Pill pill) {
		
		pills.add(pill);
	}
		
	public List<Pill> getPills() {
		
		return pills;
	}
	
	public Pill getPill(int i) {
		
		return pills.get(i);
	}
}
