package project.core.gfx;

import project.core.TicTacToe;
import project.core.logic.Board;
import project.core.logic.RealPlayer;
import pythagoras.f.IPoint;

public class GfxRealPlayer extends RealPlayer implements Updatable {
	
	private boolean isClicking;

	public boolean isClicking() {
		
		return isClicking;
	}

	public void setClicking(boolean isClicking) {
		
		this.isClicking = isClicking;
	}
	
	@Override
	public void update(int delta) {
		
		if (isClicking) {	
			GfxGame.getInstance();
			IPoint mouse = TicTacToe.getMousePointer();
			
			for (Board board : GfxGame.getInstance().getBoards()) {

				IPoint newPointer = ((GfxBoard) board).transformPointer(mouse);
				if (((Drawable) board).getRectangle().contains(newPointer.x(), newPointer.y())) {
					if (GfxGame.getInstance().isLegalClick((GfxBoard) board)) {
						
						pythagoras.i.IPoint coords = GfxGame.gfxToCoords(newPointer.x(), newPointer.y(), (GfxBoard) board);
						GfxGame.getInstance().placePill(coords.y(), coords.x(), board);
					}	
				}
			}
			
			isClicking = false;
		}
	}
}
