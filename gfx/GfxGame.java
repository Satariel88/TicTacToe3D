package project.core.gfx;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import project.core.TicTacToe;
import project.core.logic.Board;
import project.core.logic.Game;
import project.core.logic.Player;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class GfxGame extends Game implements Updatable, Drawable, MouseListener {
	
	private static final String IMAGE_PATH = "images/bg.png";
	private static final String PACMAN_PATH = "images/pacmanwins.png";
	private static final String BLINKY_PATH = "images/blinkywins.png";
	private static final int LOADING_TIME = 33;
	private static final float DEPTH = 0.0f;
	private static final IDimension SIZE = new Dimension(1280.0f, 800.0f);
	public static final IPoint POS = new Point(0.0f, 0.0f);
	public static final IPoint WIN_POS = new Point(532.0f, 107.0f);
	private static Image image;
	private static Image pacmanWinsImage;
	private static Image blinkyWinsImage;
	private static GfxGame instance;
	private static int loadedTime;
	private Decorator decorator;
	private boolean isAwin;
	private ImageLayer pacmanWins;
	private ImageLayer blinkyWins;
	
	private GfxGame() {
		
		pacmanWins = graphics().createImageLayer(pacmanWinsImage);
		pacmanWins.setVisible(false);
		pacmanWins.setDepth(DEPTH + 1);
		pacmanWins.setTranslation(WIN_POS.x(), WIN_POS.y());
		graphics().rootLayer().add(pacmanWins);
		blinkyWins = graphics().createImageLayer(blinkyWinsImage);
		blinkyWins.setVisible(false);
		blinkyWins.setDepth(DEPTH + 1);
		blinkyWins.setTranslation(WIN_POS.x(), WIN_POS.y());
		graphics().rootLayer().add(blinkyWins);
	}
	
	public static GfxGame getInstance() {
		
		if (instance == null)
			instance = new GfxGame();
		
		return instance;
	}
	
	@Override
	public void createGame() {
		
		setRealPlayer(new GfxRealPlayer());
		setPcPlayer(new GfxPCPlayer());
		
		setBoard(new GfxBoard(0.0f, 0.0f));
		setBoard(new GfxBoard(1.1f, 0.9f));
		setBoard(new GfxBoard(2.2f, 1.8f));
		setBoard(new GfxBoard(3.3f, 2.7f));
		
		for (int i = 0; i < BOARDS; i++)
			getBoard(i).initBoard(getRealPlayer(), getPcPlayer());
		
		decorator = new Decorator(image, new Rectangle(POS, SIZE), DEPTH);
	}
	
	public static void loadImages(AssetWatcher watcher) {
		
		image = assets().getImage(IMAGE_PATH);
		watcher.add(image);
		
		pacmanWinsImage = assets().getImage(PACMAN_PATH);
		watcher.add(pacmanWinsImage);
		
		blinkyWinsImage = assets().getImage(BLINKY_PATH);
		watcher.add(blinkyWinsImage);
	}
	
	@Override
	public boolean placePill(int row, int col, Board board) {
		
		if (isMoveLegal(row, col, board)) {
		
			Player currentPlayer = isPlayerTurn() ? getRealPlayer() : getPcPlayer();
			GfxPill pill = isPlayerTurn() ? new GfxPlayerPill(row, col, board) : new GfxPCPill(row, col, board);
			board.get(row, col).placePill(pill);
			currentPlayer.addPill(pill);
			setPlayerTurn(!isPlayerTurn());
			isAwin = isAwin(row, col, board);
			return isAwin;
		}
		
		return false;
	}
	
	public static IPoint coordsToGfx(int row, int col, GfxBoard board) {
		
		float x = board.getPosition().x() + GfxCell.DIST.x() + col * (GfxCell.DIST.x() + GfxCell.SIZE.width());
		float y = board.getPosition().y() + GfxCell.DIST.y() + row * (GfxCell.DIST.y() + GfxCell.SIZE.height());
		return new Point(x, y);
	}
	
	public static pythagoras.i.IPoint gfxToCoords(float x, float y, GfxBoard board) {

		int row = (int) Math.floor((y - (board.getPosition().y() + GfxCell.DIST.y())) / (GfxCell.SIZE.height() + GfxCell.DIST.y())); 
		int col = (int) Math.floor((x - (board.getPosition().x() + GfxCell.DIST.x())) / (GfxCell.SIZE.width() + GfxCell.DIST.x())); 
		return new pythagoras.i.Point(col, row);
	}
	
	@Override
	public MouseListener onMouseDown(ButtonEvent event) {
				
		((GfxRealPlayer) getRealPlayer()).setClicking(true);
		return this;
	}

	@Override
	public MouseListener onMouseMove(MotionEvent event) {
		
		return this;
	}

	@Override
	public MouseListener onMouseUp(ButtonEvent event) {
		
		((GfxRealPlayer) getRealPlayer()).setClicking(false);
		return this;
	}

	@Override
	public MouseListener onMouseWheelScroll(WheelEvent event) {

		return this;
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
	
	public boolean isLegalClick(GfxBoard board) {
		
		IPoint newPointer = board.transformPointer(TicTacToe.getMousePointer());
		return board.isLegalClick(gfxToCoords(newPointer.x(), newPointer.y(), board));
	}
	
	@Override
	public void destroy() {
		
		decorator.destroy();
	}
	
	@Override
	public void update(int delta) {
		
		if (loadedTime < LOADING_TIME)
			loadedTime += delta;
		
		else if (!isAwin) {
			
    		if (isPlayerTurn())
    			((GfxRealPlayer) getRealPlayer()).update(delta);
    		
    		else
    			((GfxPCPlayer) getPcPlayer()).update(delta);
		}
		
		else {
			
			if (!isPlayerTurn())
				blinkyWins.setVisible(true);
			
			else
				pacmanWins.setVisible(true);
		}
	}
}
