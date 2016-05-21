package project.core.gfx;

import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;

public interface Drawable {

	ImageLayer getLayer();
	IRectangle getRectangle();
	boolean visible();
	void setVisible(boolean visible);
	void setTranslation(IPoint pos);
	void setTranslation(float x, float y);
	IPoint getPosition();
	void destroy();
}
