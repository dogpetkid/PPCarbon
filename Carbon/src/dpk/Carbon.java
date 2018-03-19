package dpk;

import javax.swing.JFrame;

public class Carbon {
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(Game.TITLE);
		frame.add(game);
		frame.setSize(Game.WIDTH, Game.HEIGHT);
		frame.setResizable(false);
//		frame.setFocusable(true);
//		frame.setAutoRequestFocus(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}

}
