package render.sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import render.Texture;

public class Sprite {
	
	private BufferedImage image;
	
	/**
	 * X and Y are in terms of the corner being 0,0.
	 * @param spritesheet Sheet sprite is on.
	 * @param x 
	 * @param y
	 */
	public Sprite(SpriteSheet spritesheet, int x, int y) {
		// doing ++ causes the spritesheet corner to be 0,0
		x++;
		y++;
		int w = spritesheet.getWidth();
		int h = spritesheet.getHeight();
		//x*w grabs the right side of the sprite so -w gets the right side
		//y*h grabs the bottom side of the sprite so -h gets the top side
		this.image = spritesheet.getTexture().getImage().getSubimage(x*w-w, y*h-h, w, h);		
	}
	
	public Sprite(String texName) {
		Texture tex = new Texture(texName);
		image = tex.getImage();
	}
	
	public void render(Graphics g, double x, double y) {
		g.drawImage(image, (int) x, (int) y, null);
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}

}
