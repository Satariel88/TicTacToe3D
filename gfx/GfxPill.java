package project.core.gfx;

import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import project.core.logic.Board;
import project.core.logic.Pill;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Rectangle;

public abstract class GfxPill extends Pill implements Drawable {

	protected static final String IMAGE_PATH = "images/pills.png";
	private static final float DEPTH = 3.0f;
	public static final IDimension SIZE = new Dimension(38.0f, 39.0f);
	private Decorator pillDecorator;
	private static Image image;
	
	public GfxPill(boolean color, int row, int col, Board board, Image image) {
		
		super(color);
		IPoint pos = ((GfxBoard) board).transformPosition(GfxGame.coordsToGfx(row, col, (GfxBoard) board));
		IRectangle rectangle = new Rectangle(pos.add(GfxCell.SIZE.width() / 2 - SIZE.width() / 2, GfxCell.SIZE.height() / 2 - SIZE.height() / 2), SIZE);
		pillDecorator = new Decorator(image, rectangle, DEPTH);
	}

	public static void loadImages(AssetWatcher watcher) {
		
		GfxPlayerPill.loadImages(watcher);
		GfxPCPill.loadImages(watcher);
	}

	@Override
	public ImageLayer getLayer() {

		return pillDecorator.getLayer();
	}

	@Override
	public IRectangle getRectangle() {

		return pillDecorator.getRectangle();
	}

	@Override
	public boolean visible() {

		return pillDecorator.visible();
	}

	@Override
	public void setVisible(boolean visible) {

		pillDecorator.setVisible(visible);
	}

	@Override
	public void setTranslation(IPoint pos) {

		pillDecorator.setTranslation(pos);
	}

	@Override
	public void setTranslation(float x, float y) {
		
		pillDecorator.setTranslation(x, y);
	}

	@Override
	public IPoint getPosition() {
		
		return pillDecorator.getPosition();
	}
	
	@Override
	public void destroy() {
		
		pillDecorator.destroy();
	}
	
	protected static Image getImage() {
		return image;
	}
	
	protected static void setImage(Image image) {
		GfxPill.image = image;
	}
	
	protected Decorator getDecorator() {
		
		return pillDecorator;
	}
}
