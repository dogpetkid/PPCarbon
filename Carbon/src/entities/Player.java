package entities;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import dpk.Game;
import input.KeyInput;
import render.sprites.Sprite;
import states.LevelState;
import world.Tile;

/**
 * Carbon atom player.
 */
public class Player extends Mob{
	
	/**
	 *  (relative to player) 0 none, 1 left, 2 up, 3 down, 4 right
	 *  i.e. if in spot 1, the bonded atom would be left of the player
	 */
	private AnyAtom[] bonds = {null, null, null, null, null};

	public Player(Sprite sprite, double x, double y, LevelState state) {
		super(sprite, x, y, state);
		state.setPlayer(this);
	}

	//TODO: fix the issue of bonded atoms acting weirdly compared to individual ones
	
	@Override
	public void tick() {
		dx=0;
//		dy=0;
		if(KeyInput.isDown(KeyEvent.VK_A) || KeyInput.isDown(KeyEvent.VK_LEFT)) dx-= 2;
		if(KeyInput.wasPressed(KeyEvent.VK_W) || KeyInput.wasPressed(KeyEvent.VK_UP)) {
			rise(7);
			maxDY = 6; // to slow fall
		} else {
			maxDY = 8; // to free fall
		}
		if(KeyInput.isDown(KeyEvent.VK_S) || KeyInput.isDown(KeyEvent.VK_DOWN)) maxDY = 10; // to hard fall
		if(KeyInput.isDown(KeyEvent.VK_D) || KeyInput.isDown(KeyEvent.VK_RIGHT)) dx+= 2;
		super.tick();
	}
	
	/**
	 * Bonds the player and an atom.
	 * @param atom The atom that is bonding (use: <code>this</code>)
	 * @param bondDirection Direction relative to the player, same variable as in class <code>AnyAtom</code>
	 */
	protected void bond(AnyAtom atom, int bondDirection) {
		bonds[bondDirection] = atom;
	}
	
	@Override
	protected boolean hasHorizontalCollision() {
		boolean willReturn = false;
		willReturn |= super.hasHorizontalCollision();
		
		for(int i=0; i<bonds.length; i++) {
			AnyAtom a = bonds[i];
			if(a==null) continue;
			boolean bondTrue = a.hasHorizontalCollision();
			willReturn |= bondTrue;
			
			// left had collision
			if(i==1 && bondTrue) {
				x = a.getX()+32;
			}
			
			// up had collision
			if(i==2 && bondTrue) {
				x = a.getX();
			}
			// down had collision
			if(i==3 && bondTrue) {
				x = a.getX();
			}
			
			// right had collision
			if(i==4 && bondTrue) {
				x = a.getX()-32;
			}
			
			// keep pushing out of collision until the whole 'molecule' is not colliding
			if(bondTrue) {
				forceAllMimic();
				hasHorizontalCollision();
				dx=0;
			}
		}
		
		return willReturn;
	}
	
	@Override
	public boolean hasVerticalCollision() {
		boolean willReturn = false;
		willReturn |= super.hasVerticalCollision();
		
		for(int i=0; i<bonds.length; i++) {
			AnyAtom a = bonds[i];
			if(a==null) continue;
			boolean bondTrue = a.hasVerticalCollision();
			willReturn |= bondTrue;
			
			// left had collision
			if(i==1 && bondTrue) {
				y = a.getY();
			}
			
			// up had collision
			if(i==2 && bondTrue) {
				y = a.getY()+32;
			}
			// down had collision
			if(i==3 && bondTrue) {
				y = a.getY()-32;
			}
			
			// right had collision
			if(i==4 && bondTrue) {
				y = a.getY();
			}
			
			// keep pushing out of collision until the whole 'molecule' is not colliding
			if(bondTrue) {
				forceAllMimic();
				hasVerticalCollision();
				dy=0;
			}
		}
		
		return willReturn;
	}
	
	//TODO: Finish stop overshoot (horz)
	@Override
	protected boolean stopXOvershoot(Rectangle next) {
		boolean willReturn = false;
		willReturn |= super.stopXOvershoot(next);
		
		for(int i=0; i<bonds.length; i++) {
			AnyAtom a = bonds[i];
			if(a==null) continue;
		}
		
		return willReturn;
	}
	
	//TODO: Finish stop overshoot (vert)
	@Override
	protected boolean stopYOvershoot(Rectangle next) {
		boolean willReturn = false;
		willReturn |= super.stopYOvershoot(next);
		
		for(int i=0; i<bonds.length; i++) {
			AnyAtom a = bonds[i];
			if(a==null) continue;
		}
		
		return willReturn;
	}
	
	@Override
	protected void checkBelow() {
		super.checkBelow();
		for(AnyAtom a : bonds) {
			if(a==null) continue;
			a.checkBelow();
			// if anything can rise, then the molecule as a whole can rise
			canRise |= a.canRise();
		}
		// turns gravity on or off depending on whether the molecule is on the ground (or canRise)
		landing=!canRise;
		for(AnyAtom a : bonds) {
			if (a==null) continue;
			a.setLanding(!canRise);
		}
	}
	
	/**
	 * Forcibly adjusts all the bonded atoms to re-center them around the player.
	 */
	protected void forceAllMimic() {
		for(int i=0; i<bonds.length; i++) {
			AnyAtom a = bonds[i];
			if(a==null) continue;
			a.mimic();
		}
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}

	public AnyAtom[] getBonds() {
		return bonds;
	}
}
