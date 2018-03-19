package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dpk.Game;
import render.sprites.Sprite;
import states.LevelState;

public abstract class Entity {
	
	protected double x, y;
	protected Sprite sprite;
	protected boolean visible;
	protected LevelState state;
	
	public Entity(Sprite sprite, double x, double y, LevelState state) {
		this(sprite, x, y, state, true);
	}
	
	public Entity(Sprite sprite, double x, double y, LevelState state, boolean visible) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.visible = visible;
		this.state = state;
		this.state.addEntity(this);
	}
	
	public abstract void tick();
	
	public void render(Graphics2D g) {
		if(visible) sprite.render(g, x, y);
		if(Game.debug) {
			if(this.getClass() == Player.class) g.setColor(Color.PINK);
			else g.setColor(Color.MAGENTA);
			g.draw(getLeft());
			g.draw(getTop());
			g.draw(getBottom());
			g.draw(getRight());
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
	}
	
	public Rectangle getLeft() {
		return new Rectangle(
				(int) x,
				(int) y+(sprite.getHeight()/8),
				(int) sprite.getWidth()/8,
				(int) 6*sprite.getHeight()/8);
	}
	
	public Rectangle getTop() {
		return new Rectangle(
				(int) x+(sprite.getWidth()/8),
				(int) y,
				(int) 6*sprite.getWidth()/8,
				(int) sprite.getHeight()/8);
	}
	
	public Rectangle getBottom() {
		return new Rectangle(
				(int) x+(sprite.getWidth()/8),
				(int) y+(7*sprite.getHeight()/8),
				(int) 6*sprite.getWidth()/8,
				(int) sprite.getHeight()/8);
	}
	
	public Rectangle getRight() {
		return new Rectangle(
				(int) x+(7*sprite.getWidth()/8),
				(int) y+(sprite.getHeight()/8),
				(int) sprite.getWidth()/8,
				(int) 6*sprite.getHeight()/8);
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
