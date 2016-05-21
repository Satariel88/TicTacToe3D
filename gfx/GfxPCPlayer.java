package project.core.gfx;

import project.core.logic.PCPlayer;
import project.core.search.AlphaBeta;

public class GfxPCPlayer extends PCPlayer implements Updatable {
	
	@Override
	public void update(int delta) {

		AlphaBeta ab = new AlphaBeta();
		ab.minimaxAlphabeta(64 - (getPills().size() * 2 + 1));
		int move = ab.selectMove();

		GfxGame.getInstance().placePill((move % 16) / 4, move % 4, GfxGame.getInstance().getBoard(move/16));
	}
	
}
