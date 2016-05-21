package project.core.gfx;

import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;

public interface MouseListener {
	
	public MouseListener onMouseDown(ButtonEvent event);
	public MouseListener onMouseMove(MotionEvent event);
	public MouseListener onMouseUp(ButtonEvent event);
	public MouseListener onMouseWheelScroll(WheelEvent event);
}
