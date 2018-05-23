package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dpk.Game;
import render.sprites.Sprite;

public class Tile {
	
	protected float x, y;
	protected Sprite sprite;
	protected boolean solid;
	
	
	public Tile(Sprite sprite, float x, float y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.solid = true;
	}
	
	public void render(Graphics2D g) {
		sprite.render(g, x, y);
		if(Game.debug) {
			g.setColor(Color.WHITE);
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
	
}
