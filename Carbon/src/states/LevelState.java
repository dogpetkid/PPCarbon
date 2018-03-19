package states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import dpk.Game;
import entities.Entity;
import entities.Player;
import render.sprites.Sprite;
import world.Tile;

public abstract class LevelState implements State{

	private ArrayList<Entity> entities;
	private ArrayList<Tile> tiles;
	private Player player;
	
	/**
	 * Use super.init().
	 */
	@Override
	public void init() {
		entities = new ArrayList<Entity>();
		tiles = new ArrayList<Tile>();
		
		for(float x=0; x<Game.WIDTH+32; x+=32) {
			tiles.add(new Tile(new Sprite(Game.sheet1, 0, 2),x,0));
			tiles.add(new Tile(new Sprite(Game.sheet1, 0, 2),x,Game.HEIGHT-61));
		}
		for(float y=0; y<Game.HEIGHT+32; y+=32) {
			tiles.add(new Tile(new Sprite(Game.sheet1, 0, 2),0,y));
			tiles.add(new Tile(new Sprite(Game.sheet1, 0, 2),Game.WIDTH-38,y));
		}
	}

	/**
	 * Use super.tick().
	 */
	@Override
	public void tick(StateManager stateManager) {
		for(Entity e : entities) {
			e.tick();
		}
	}

	/**
	 * Use super.render().
	 */
	@Override
	public void render(Graphics2D g) {
		for(Entity e : entities) {
			e.render(g);
		}
		for(Tile t : tiles) {
			t.render(g);
		}
	}

	/**
	 * Use super.exit() AFTER other statements within your exit.
	 */
	@Override
	public void exit() {
		entities.clear();
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void setPlayer(Player player) {
		if(this.player==null) {
			this.player = player;
		} else {
			System.err.println("Can not have more than 1 player in state <"+getName()+">!");
		}
	}
	
	public ArrayList<Entity> getEntitites() {
		return entities;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public Player getPlayer() {
		return player;
	}
}
