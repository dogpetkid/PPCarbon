package states.rooms;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import dpk.Game;
import entities.Oatom;
import entities.Player;
import render.sprites.Sprite;
import states.LevelState;
import states.State;
import states.StateManager;
import utils.Fonts;

public class room2 extends LevelState implements State{
	
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
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		Fonts.drawString(g, new Font("Optima",Font.PLAIN, 32), Color.RED, "Copy of room1");
		Fonts.drawString(g, new Font("Optima",Font.PLAIN, 12), Color.RED, "For state change demonstration purposes", Game.HEIGHT/2+48);
	}
	
	@Override
	public String getName() {
		return "room2";
	}

}

