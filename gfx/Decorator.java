package project.core.gfx;

import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import static playn.core.PlayN.graphics;

public class Decorator {

	private ImageLayer layer;
	private IRectangle rectangle;
	
	public Decorator(Image image, IRectangle rectangle, float depth) {
		
		this(image, rectangle, depth, true);
	}
	
	public Decorator(Image image, IRectangle rectangle, float depth, boolean visible) {
		
		this.rectangle = rectangle;
		layer = createLayer(image, depth, visible);
	}

	public static void loadImages(AssetWatcher watcher) {
		
		GfxTitle.loadImages(watcher);
		GfxGame.loadImages(watcher);
		GfxBoard.loadImages(watcher);
		GfxCell.loadImages(watcher);
		GfxPill.loadImages(watcher);
	}
	
	private ImageLayer createLayer(Image image, float depth, boolean visible) {

		ImageLayer layer = graphics().createImageLayer(image);
		layer.setVisible(visible);
		layer.setDepth(depth);
		layer.setTranslation(rectangle.x(), rectangle.y());
		graphics().rootLayer().add(layer);
		return layer;
	}

	public ImageLayer getLayer() {

		return layer;
	}

	public IRectangle getRectangle() {

		return rectangle;
	}

	public boolean visible() {

		return layer.visible();
	}

	public void setVisible(boolean visible) {

		layer.setVisible(visible);
	}

	public void setTranslation(IPoint pos) {
		
		layer.setTranslation(pos.x(), pos.y());
	}

	public void setTranslation(float x, float y) {

		layer.setTranslation(x, y);
	}

	public IPoint getPosition() {

		return new Point(layer.tx(), layer.ty());
	}

	public void destroy() {
		
		layer.destroy();
	}
}
