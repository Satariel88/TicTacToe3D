package project.core.gfx;

import static playn.core.PlayN.assets;

import static playn.core.PlayN.graphics;
import playn.core.AssetWatcher;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import project.core.gfx.Decorator;
import project.core.gfx.Drawable;
import project.core.gfx.GfxBoard;
import project.core.logic.Board;
import project.core.logic.PCPlayer;
import project.core.logic.RealPlayer;
import pythagoras.f.Transforms;
import pythagoras.f.AffineTransform;
import pythagoras.f.Dimension;
import pythagoras.f.FloatMath;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class GfxBoard extends Board implements Drawable {

	private static final String IMAGE_PATH = "images/board.png";
	private static final float DEPTH = 1.0f;
	private static final IDimension SIZE = new Dimension(205.0f, 205.0f);
	public static final IPoint DEF_POS = new Point(50.0f, 30.0f);
	private static final float DIST = -10.0f;
	private static Image image;
	private IPoint pos;
	private Decorator decorator;
	private AffineTransform transform;
	private GroupLayer boardGroupLayer;
	private GroupLayer cellGroupLayer;
	
	public GfxBoard(float width, float height) {
	
		pos = DEF_POS.add(width * (SIZE.width() + DIST), height * (SIZE.width() + DIST));
		decorator = new Decorator(image, new Rectangle(pos, SIZE), DEPTH);
		initTransform();
		boardGroupLayer = graphics().createGroupLayer();
		cellGroupLayer = graphics().createGroupLayer();
		boardGroupLayer.setDepth(DEPTH);
		cellGroupLayer.setDepth(DEPTH + 1);
		boardGroupLayer.add(decorator.getLayer());
		graphics().rootLayer().add(boardGroupLayer);
		
		boardGroupLayer.transform().setTransform(transform.m00, transform.m01, transform.m10, transform.m11, transform.tx, transform.ty);
		cellGroupLayer.transform().setTransform(transform.m00, transform.m01, transform.m10, transform.m11, transform.tx, transform.ty);
	}
	
	@Override
	public void initBoard(RealPlayer player, PCPlayer pc) {

		for (int row = 0; row < HEIGHT; row++)
			for (int col = 0; col < WIDTH; col++) {
			
				GfxCell cell = new GfxCell(row, col, this);
				set(row, col, cell);
				cellGroupLayer.add(cell.getLayer());
			}
		
		graphics().rootLayer().add(cellGroupLayer);
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
	
	public void initTransform() {
		
		transform = new AffineTransform();
		float angleFactor = FloatMath.toRadians(35);
		float scaleFactor = 0.86f;
		AffineTransform scaleTransform = new AffineTransform(1, 0, 0, scaleFactor, 0, 0);
		float shearFactor = FloatMath.tan(-angleFactor);
		AffineTransform shearTransform = new AffineTransform(1, 0, shearFactor, 1, 0, 0);
		float sin = FloatMath.sin(angleFactor);
		float cos = FloatMath.cos(angleFactor);
		AffineTransform rotationTransform = new AffineTransform(cos, sin, -sin, cos, 0, 0);
		Transforms.multiply(rotationTransform, Transforms.multiply(shearTransform, scaleTransform, transform), transform);
		transform.translate(80, -175);
	}
	
	public IPoint transformPointer(IPoint pointer) {
		
		Point newPointer = new Point();
		return transform.inverseTransform(pointer, newPointer);
	}
	
	public IPoint transformPosition(IPoint position) {
	
		Point newPosition = new Point();
		return transform.transform(position, newPosition).subtract(25, 10);
	}
}
