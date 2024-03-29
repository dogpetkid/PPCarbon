package entities;

import java.awt.event.KeyEvent;

import input.KeyInput;
import render.sprites.Sprite;
import states.LevelState;

/**
 * All mobs that are non-player 'atoms'.
 */
public class AnyAtom extends Mob{

	protected boolean bonded = false;
	/**
	 *  (RELATIVE TO PLAYER) 0 none, 1 left, 2 up, 3 down, 4 right
	 *  i.e. if bondDirection was 1, the bonded atom would be left of the player
	 */
	protected int bondDirection = 0; 

	public AnyAtom(Sprite sprite, double x, double y, LevelState state) {
		super(sprite, x, y, state);
	}
	
	public AnyAtom(Sprite sprite, double x, double y, LevelState state, boolean visible) {
		super(sprite, x, y, state,visible);
	}
	
	@Override
	public void tick() {
		super.tick();
		if(bonded) mimic();
		else bonded = checkBonded();
	}
	
	protected boolean checkBonded() {
		boolean willBond = false;
		Player e = state.getPlayer();
		
		// traveling right
		if(getBounds().intersects(e.getLeft())) {bondDirection = 1; willBond=true;}
		// traveling left
		if(getBounds().intersects(e.getRight())) {bondDirection = 4; willBond=true;}
		// landed
		if(getBounds().intersects(e.getTop())) {bondDirection = 2; willBond=true;}
		// risen
		if(getBounds().intersects(e.getBottom())) {bondDirection = 3; willBond=true;}
		
		if(willBond) e.bond(this, bondDirection);
		return willBond;
	}
	
	@Deprecated
	public void oldMimic() {
		dx=0;
		if(KeyInput.isDown(KeyEvent.VK_A)) dx-= 2;
		if(KeyInput.isDown(KeyEvent.VK_W)) {
			rise(10);
			maxDY = 6; // to slow fall
		} else {
			maxDY = 8; // to free fall
		}
		if(KeyInput.isDown(KeyEvent.VK_S)) maxDY = 10; // to hard fall
		if(KeyInput.isDown(KeyEvent.VK_D)) dx+= 2;
	}
	
	/**
	 * Mimics the players movements after bonded.
	 */
	public void mimic() {
		Player player = state.getPlayer();
		dx=player.getDx();
		dy=player.getDy();
		switch(bondDirection) {
		case 0:
			break;
		case 1:
			// left
			this.x = player.getX()-32;
			this.y = player.getY();
			break;
		case 2:
			// up
			this.x = player.getX();
			this.y = player.getY()-32;
			break;
		case 3:
			// down
			this.x = player.getX();
			this.y = player.getY()+32;
			break;
		case 4:
			// right
			this.x = player.getX()+32;
			this.y = player.getY();
			break;
		}	
	}
	
	public void setLanding(boolean landing) {
		this.landing = landing;
	}
	
	public void setCanRise(boolean canRise) {
		this.canRise = canRise;
	}
	
	public boolean getLanding() {
		return landing;
	}
	
	public boolean canRise() {
		return canRise;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
