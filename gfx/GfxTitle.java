package project.core.gfx;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class GfxTitle implements Updatable, Drawable, MouseListener {
	
	private static final String IMAGE_PATH = "images/title.png";
	private static final float DEPTH = 0.0f;
	private static final IDimension SIZE = new Dimension(1280.0f, 800.0f);
	public static final IPoint POS = new Point(0.0f, 0.0f);
	public static final IRectangle BUTTON_FIRST = new Rectangle(new Point(467.0f, 352.0f), new Dimension(345.0f, 75.0f));
	public static final IRectangle BUTTON_SECOND = new Rectangle(new Point(467.0f, 449.0f), new Dimension(345.0f, 75.0f));
	private static Image image;
	private static GfxTitle instance;
	
	private Decorator decorator;
	
	private GfxTitle() {
		
		createMenu();
	}
	
	public static GfxTitle getInstance() {
		
		if (instance == null)
			instance = new GfxTitle();
		
		return instance;
	}
	
	private void createMenu() {
		
		decorator = new Decorator(image, new Rectangle(POS, SIZE), DEPTH);
	}
	
	public static void loadImages(AssetWatcher watcher) {
		
		image = assets().getImage(IMAGE_PATH);
		watcher.add(image);
	}
	
	
	@Override
	public ImageLayer getLayer() {

		return decorator.getLayer();
	}

	@Override
	public IRectangle getRectangle() {

		return decorator.getRectangle();
	}

	@Override
	public boolean visible() {

		return decorator.visible();
	}

	@Override
	public void setVisible(boolean visible) {

		decorator.setVisible(visible);
	}
	
	@Override
	public void setTranslation(IPoint pos) {
		decorator.setTranslation(pos);
	}

	@Override
	public void setTranslation(float x, float y) {
		
		decorator.setTranslation(x, y);
	}

	@Override
	public IPoint getPosition() {
		
		return decorator.getPosition();
	}
	
	@Override
	public void destroy() {
		
		decorator.destroy();
	}
	
	@Override
	public void update(int delta) {

	}

	@Override
	public MouseListener onMouseDown(ButtonEvent event) {
		
		if (BUTTON_FIRST.contains(new Point(event.localX(), event.localY()))) {
			
			GfxGame.getInstance().createGame();
			GfxGame.getInstance().setPlayerTurn(true);
			setVisible(false);
			return GfxGame.getInstance();
		}
		
		if (BUTTON_SECOND.contains(new Point(event.localX(), event.localY()))) {
			
			GfxGame.getInstance().createGame();
			GfxGame.getInstance().setPlayerTurn(false);
			setVisible(false);
			return GfxGame.getInstance();
		}
		
		return this;
	}

	@Override
	public MouseListener onMouseMove(MotionEvent event) {
		
		return this;
	}

	@Override
	public MouseListener onMouseUp(ButtonEvent event) {
		
		return this;
	}

	@Override
	public MouseListener onMouseWheelScroll(WheelEvent event) {
		
		return this;
	}
}
