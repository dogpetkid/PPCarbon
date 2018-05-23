package entities;

import java.awt.Rectangle;

import render.sprites.Sprite;
import states.LevelState;
import world.Tile;

/**
 * Generic mobile entity
 */
public abstract class Mob extends Entity{
	
	protected double dx, dy;
	protected double maxDY; // terminal velocity
	protected double gravity;
	protected boolean landing; // or falling
	protected boolean canRise; // or canJump

	public Mob(Sprite sprite, double x, double y, LevelState state) {
		this(sprite, x, y, state, true);
		}

	public Mob(Sprite sprite, double x, double y, LevelState state, boolean visible) {
		super(sprite, x, y, state, visible);
		gravity = 0.3;
		maxDY = 6;
		landing = true;
		canRise = false;
		}
	
	@Override
	public void tick(){
		move();
		fall();
	}
	
	public void move() {
		Rectangle next = getNextBounds(dx,dy);
		//TODO: Somehow these (4) collision statements allow the player to climb walls
		stopYOvershoot(next);
		stopXOvershoot(next);
		if(!hasVerticalCollision()) y+= dy;
		if(!hasHorizontalCollision()) x+= dx;
	}
	
	public void jumpTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getNextBounds(double dx, double dy) {
		return new Rectangle((int) (x+dx), (int) (y+dy), sprite.getWidth(), sprite.getHeight());
	}
	
	protected boolean hasHorizontalCollision() {
		boolean willReturn = false;
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			// traveling right
			if(getBounds().intersects(t.getLeft()) && dx > 0) {
				dx=0;
				x=t.getBounds().getMinX()-32;
				willReturn |= true;
				}
			// traveling left
			if(getBounds().intersects(t.getRight()) && dx < 0) {
				dx=0;
				x=t.getBounds().getMinX()+32;
				willReturn |= true;
			}
		}
		
		return willReturn;
	}
	
	protected boolean hasVerticalCollision() {
		boolean willReturn = false;
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			// landing
			if(getBounds().intersects(t.getTop()) && dy > 0) {
				dy=0;
				// the reason to set the y value is that it bumps the object of collision out
				// of the object it collides with
				y=t.getBounds().getMinY()-32;
				willReturn |= true;
			}
			// rising
			if(getBounds().intersects(t.getBottom()) && dy < 0) {
				dy=0;
				// see above
				y=t.getBounds().getMinY()+32;
				willReturn |= true;
			}
		}
		return willReturn;
	}
	
	/**
	 * Stops overshooting collisions on X axis.
	 * @param next Bounds of {@code this} in next tick
	 * @return If the mob had its dx change to prevent a collision.
	 */
	protected boolean stopXOvershoot(Rectangle next) {
		boolean willReturn = false;
		// 1 pixel in the opposite direction of dx
		int reversePixel = (int) (-dx/Math.abs(dx));
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			
			if(next.intersects(t.getBounds()) && (dx!=0)) {
				this.dx+= reversePixel;
				stopXOvershoot(getNextBounds(this.dx,dy));
				willReturn |= true;
			}
			
		}
		return willReturn;
	}
	
	/**
	 * Stops overshooting collisions on Y axis.
	 * @param next Bounds of {@code this} in next tick
	 * @return  If the mob had its dy change to prevent a collision.
	 */
	protected boolean stopYOvershoot(Rectangle next) {
		boolean willReturn = false;
		// 1 pixel in the opposite direction of dy
		int reversePixel = (int) (-dy/Math.abs(dy));
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			
			if(next.intersects(t.getBounds()) && (dx!=0)) {
				this.dy+= reversePixel;
				stopXOvershoot(getNextBounds(dx,this.dy));
				willReturn |= true;
			}
			
		}
		return willReturn;
	}
	
	protected void fall() {
		checkBelow();
		
		if(landing) {
			dy += gravity;
			if(dy > maxDY) dy = maxDY;
		}
	}
	
	/**
	 * Properly sets the {@code landing} and {@code falling} states.
	 * (It checks tiles below by temporarily shifting the mob down.)
	 */
	protected void checkBelow() {
		// checks for tiles below mob (moves mob down 1 to check and then moves player back up)
		boolean tileBelow = false;
		y++;
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			if(getBounds().intersects(t.getBounds())) { // changed from t.getTop()
				tileBelow |= true;
			}
		}
		y--;
		
		// changes the gravity on or off depending if there is or is not ground below
		// the object
		if(tileBelow) {
			landing = false;
			canRise = true;
		}
		else {
			landing = true;
			canRise = false;
		}
	}
	
	protected void rise(double riseJerk) {
		if(canRise) {
			dy -= riseJerk;
			canRise = false;
		}
	}
	
}
