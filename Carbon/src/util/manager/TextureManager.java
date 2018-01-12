package util.manager;

import java.awt.image.BufferedImage;

public class TextureManager extends RecourceManager{
	
	private BufferedImage image;
	
	public TextureManager(BufferedImage image) {
		this.image = image;
	}
		
	public BufferedImage getImage() {
		return image;
	}

}
