package entities;

import render.sprites.Sprite;
import states.LevelState;

/**
 * Oxygen atom.
 */
public class Oatom extends AnyAtom{
	
	public Oatom(Sprite sprite, double x, double y, LevelState state) {
		super(sprite, x, y, state);
		this.bonded = false;
	}
	
	@Override
	public void tick() {
		//if(bonded) mimic();
		//else checkBonded();
		super.tick();
	}
}
