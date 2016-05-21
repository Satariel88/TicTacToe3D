package project.core.gfx;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import project.core.logic.Board;

public class GfxPlayerPill extends GfxPill {

	public GfxPlayerPill(int row, int col, Board board) {
		
		super(true, row, col, board, getImage().subImage(0, 0, SIZE.width(), SIZE.height()));
	}
	
	public static void loadImages(AssetWatcher watcher)	{
		
		setImage(assets().getImage(IMAGE_PATH));
		watcher.add(getImage());
	}
}
