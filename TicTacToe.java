package project.core;

import static playn.core.PlayN.*;

import playn.core.AssetWatcher;
import playn.core.Game;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import project.core.gfx.Decorator;
import project.core.gfx.GfxTitle;
import project.core.gfx.MouseListener;
import project.core.gfx.Updatable;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class TicTacToe extends Game.Default {
	
	private MouseListener gameState;
	private AssetWatcher watcher;
	private static IPoint mousePointer;
	
	public TicTacToe() {
		super(33); // call update every 33ms (30 times per second)
	}

	@Override
	public void init() {
	  
		setMouseListener();
		loadImages();
		gameState = GfxTitle.getInstance();
	}

	public void loadImages() {
	
		watcher = new AssetWatcher(new AssetWatcher.Listener() {
			
		  	@Override
		  	public void error(Throwable e) {
			  
				log().error(e.getMessage());
		  	}

		  	@Override
		  	public void done() {
		  	}
		
	  	});
		
		watcher.start();
	  	Decorator.loadImages(watcher);
	}
	
	public void setMouseListener() {
		
		mousePointer = new Point();

		mouse().setListener(new Mouse.Listener() {
			
			@Override
			public void onMouseDown(ButtonEvent event) {
				
				gameState = gameState.onMouseDown(event);
			}

			@Override
			public void onMouseMove(MotionEvent event) {
				
				mousePointer = new Point(event.localX(), event.localY());
				gameState.onMouseMove(event);
			}

			@Override
			public void onMouseUp(ButtonEvent event) {
				
				gameState.onMouseUp(event);
			}

			@Override
			public void onMouseWheelScroll(WheelEvent event) {

				gameState.onMouseWheelScroll(event);
			}
		});
	}
	
	public static IPoint getMousePointer() {
		
		return mousePointer;
	}
	
	@Override
	public void update(int delta) {
		
		if (watcher.isDone())
			((Updatable) gameState).update(delta);
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything here!
	}
}
