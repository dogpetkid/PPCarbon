package entities;

import render.sprites.Sprite;
import states.LevelState;
import world.Tile;

/**
 * Generic atom
 */
public abstract class Mob extends Entity{

	protected double dx, dy;
	protected double maxDY; // terminal velocity
	protected double gravity;
	protected boolean landing = true; // or falling
	protected boolean canRise; // or canJump

	public Mob(Sprite sprite, double x, double y, LevelState state) {
		this(sprite, x, y, state, true);
		}

	public Mob(Sprite sprite, double x, double y, LevelState state, boolean visible) {
		super(sprite, x, y, state, visible);
		gravity = 0.5;
		maxDY = 6;
		}
	
	@Override
	public void tick(){
		move();
		fall();
	}
	
	public void move() {
		if(!hasHorizontalCollision()) x+= dx;
		if(!hasVerticalCollision()) y+= dy;
	}
	
	protected boolean hasHorizontalCollision() {
		boolean willReturn = false;
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			// traveling right
			if(getBounds().intersects(t.getLeft()) && dx > 0) {dx=0; willReturn=true;}
			// traveling left
			if(getBounds().intersects(t.getRight()) && dx < 0) {dx=0; willReturn=true;}
		}
		
		// unburrows *player from colliding to hard with an object after landing inside of it
		// (player can control more than 1 thing)
		if(willReturn && hasVerticalCollision()) {
			y-= .6;
		}
		
		return willReturn;
	}
	
	protected boolean hasVerticalCollision() {
		for(int i=0; i < state.getTiles().size(); i++) {
			Tile t = state.getTiles().get(i);
			// landed
			if(getBounds().intersects(t.getTop()) && dy > 0) {dy=0; landing = false; canRise = true; return true;}
			else {landing = true;}
			// risen
			if(getBounds().intersects(t.getBottom()) && dy < 0) {dy=0; landing = true; canRise = false; return true;}
		}
		return false;
	}
	
	protected void fall() {
		if(landing) {
			dy += gravity;
			if(dy > maxDY) dy = maxDY;
		}
	}
	
	protected void rise(double riseJerk) {
		if(canRise) {
			dy -= riseJerk;
			canRise = false;
		}
	}
	
}
