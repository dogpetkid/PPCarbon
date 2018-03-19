package entities;

import java.awt.event.KeyEvent;

import dpk.Game;
import input.KeyInput;
import render.sprites.Sprite;
import states.LevelState;

/**
 * Carbon atom player.
 */
public class Player extends Mob{
	
	private AnyAtom[] bonds = {null, null, null, null, null};

	public Player(Sprite sprite, double x, double y, LevelState state) {
		super(sprite, x, y, state);
		state.setPlayer(this);
	}

	@Override
	public void tick() {
		dx=0;
//		dy=0;
		if(KeyInput.isDown(KeyEvent.VK_A)) dx-= 2;
		if(KeyInput.isDown(KeyEvent.VK_W)) {
			rise(10);
			maxDY = 6; // to slow fall
		} else {
			maxDY = 8; // to free fall
		}
		if(KeyInput.isDown(KeyEvent.VK_S)) maxDY = 10; // to hard fall
		if(KeyInput.isDown(KeyEvent.VK_D)) dx+= 2;
		super.tick();
	}
	
	/**
	 * Bonds the player and an atom.
	 * @param atom The atom that is bonding (use: <code>this</code>)
	 * @param bondDirection Direction relative to the player, same variable as in class <code>AnyAtom</code>
	 */
	public void bond(AnyAtom atom, int bondDirection) {
		bonds[bondDirection] = atom;
	}
	
	@Override
	public boolean hasHorizontalCollision() {
		boolean willReturn = false;
		willReturn |= super.hasHorizontalCollision();
		for(AnyAtom a : bonds) {
			if(a==null) continue;
			willReturn |= a.hasHorizontalCollision();
		}
		
//		System.out.println("Player - Horz will return: "+willReturn);
		
		// TODO: fix player not unburrowing from ground (see horizontal and vertical collisions)
		// unburrows player, see the super function hasHorizontalCollision()
		if(willReturn && hasVerticalCollision()) {
			y-= .6;
		}
		return willReturn;
	}
	
	@Override
	public boolean hasVerticalCollision() {
		boolean willReturn = false;
		willReturn |= super.hasVerticalCollision();
		for(AnyAtom a : bonds) {
			if(a==null) continue;
			willReturn |= a.hasVerticalCollision();
			
			canRise |= a.canRise();
		}
		landing = !canRise;
		for(AnyAtom a : bonds) {
			if(a==null) continue;
			a.setCanRise(canRise);
			a.setLanding(landing);
		}
		
//		System.out.println("Player - canRise: "+canRise);
//		System.out.println("Player - landing: "+landing);
		
		return willReturn;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
