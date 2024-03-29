package states.rooms;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import dpk.Game;
import entities.AnyAtom;
import entities.Oatom;
import entities.Player;
import render.sprites.Sprite;
import states.LevelState;
import states.State;
import states.StateManager;
import utils.Fonts;

public class room1 extends LevelState implements State{
	
	@Override
	public void init() {
		super.init();
		new Player(new Sprite(Game.sheet1, 1, 0), 100, 100, this);
		new Oatom(new Sprite(Game.sheet1, 3, 0), 200, 100, this);
	}

	@Override
	public void enter() {}

	@Override
	public void tick(StateManager stateManager) {
		super.tick(stateManager);
		
		if(checkCompletion()) stateManager.setState("room2");
	}
	
	/**
	 * Check if player has bonded to the oxygen.
	 */
	@Override
	public boolean checkCompletion() {
		//TODO: fix check
		AnyAtom[] bonds = getPlayer().getBonds();
		for(int i=0;i<bonds.length;i++) {
			if(bonds[i]!=null) return true;
		}
		return false;
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		Fonts.drawString(g, new Font("Optima",Font.PLAIN, 32), Color.RED, "Carbon bonds with oxygens (red)");
	}
	
	@Override
	public String getName() {
		return "room1";
	}

}
