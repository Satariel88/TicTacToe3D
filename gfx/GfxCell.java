package project.core.gfx;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import project.core.gfx.Decorator;
import project.core.gfx.Drawable;
import project.core.gfx.GfxGame;
import project.core.logic.Cell;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class GfxCell extends Cell implements Drawable {

	private static final String IMAGE_PATH = "images/cell.png";
	private static Image image;
	private static final float DEPTH = 2.0f;
	public static final IDimension SIZE = new Dimension(50.0f, 50.0f);
	public static final IPoint DIST = new Point(1.0f, 1.0f);
	private Decorator cellDecorator;
	
	public GfxCell(int row, int col, GfxBoard board) {
		
		super(row, col);
		IRectangle rectangle = new Rectangle(GfxGame.coordsToGfx(row, col, board), SIZE);
		cellDecorator = new Decorator(image, rectangle, DEPTH);
	}
	
	public static void loadImages(AssetWatcher watcher) {
		
		image = assets().getImage(IMAGE_PATH);
		watcher.add(image);
	}
	
	@Override
	public ImageLayer getLayer() {

		return cellDecorator.getLayer();
	}

	@Override
	public IRectangle getRectangle() {

		return cellDecorator.getRectangle();
	}

	@Override
	public boolean visible() {

		return cellDecorator.visible();
	}

	@Override
	public void setVisible(boolean visible) {

		cellDecorator.setVisible(visible);
	}

	@Override
	public void setTranslation(IPoint pos) {
		cellDecorator.setTranslation(pos);
	}

	@Override
	public void setTranslation(float x, float y) {
		
		cellDecorator.setTranslation(x, y);
	}

	@Override
	public IPoint getPosition() {
		
		return cellDecorator.getPosition();
	}
	
	@Override
	public void destroy() {
		
		cellDecorator.destroy();
	}
}
