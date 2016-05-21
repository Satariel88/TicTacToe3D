package project.core.gfx;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import project.core.logic.Board;

public class GfxPCPill extends GfxPill {

	public GfxPCPill(int row, int col, Board board) {
		
		super(false, row, col, board, getImage().subImage(SIZE.width() + 1, 0, SIZE.width(), SIZE.height()));
	}
	
	public static void loadImages(AssetWatcher watcher)	{
		
		setImage(assets().getImage(IMAGE_PATH));
		watcher.add(getImage());
	}
}
