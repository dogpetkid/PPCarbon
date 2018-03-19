package dpk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import input.KeyInput;
import input.MouseInput;
import render.ui.Button;
import states.State;
import states.StateManager;
import utils.Fonts;

public class MenuState implements State{
	
	private Button[] options;
	private int currentSelection = 0;
	
	@Override
	public void init() {
		options = new Button[3];
		Font f = new Font("Optima", Font.PLAIN, 32);
		Font selectedf = new Font("Optima", Font.BOLD, 32);
		Color c = Color.DARK_GRAY;
		Color selectedc = Color.CYAN;
		options[0] = new Button("Play", 160 + 0*60, f, selectedf, c, selectedc);
		options[1] = new Button("Options", 160 + 1*60, f, selectedf, c, selectedc);
		options[2] = new Button("Exit", 160 + 2*60, f, selectedf, c, selectedc);

	}
	
	@Override
	public void enter() {}
	
	public MenuState() {}

	@Override
	public void tick(StateManager sm) {
		boolean clicked = false;
		for(int i=0; i<options.length; i++) {
			// Is moving forces keyboard precedence
			// if (the mouse is moving or is clicked) and in the box
			if(MouseInput.isActing() && options[i].intersects(new Rectangle(MouseInput.getX(),MouseInput.getY(),1,1))) {
				currentSelection = i;
				/*
				 * Clicked is set in the if statement because if you click outside any of the buttons, you have not truly clicked 
				 * a button
				 */
				clicked = MouseInput.wasReleased(MouseEvent.BUTTON1);
			}
		}
		
		if(KeyInput.wasPressed(KeyEvent.VK_UP) || KeyInput.wasPressed(KeyEvent.VK_W)) {
			currentSelection--;
			// Rotates Selection
//			if(currentSelection<0) currentSelection = options.length-1;
			// Caps Selection
			if(currentSelection<0) currentSelection = 0;
		}
		
		if(KeyInput.wasPressed(KeyEvent.VK_DOWN) || KeyInput.wasPressed(KeyEvent.VK_S)) {
			currentSelection++;
			// Rotates Selection
//			if(currentSelection>=options.length) currentSelection = 0;
			// Caps Selection
			if(currentSelection>=options.length) currentSelection = options.length-1;
		}
		
		if(clicked || KeyInput.wasPressed(KeyEvent.VK_ENTER)) select(sm);
	}
	
	private void select(StateManager sm) {
		switch (currentSelection) {
			case 0:
				if(Game.debug) System.out.println("Play");
				sm.setState("room1");
				break;
			case 1:
				if(Game.debug) System.out.println("Options");
				sm.setState("options");
				break;
			case 2:
				if(Game.debug) System.out.println("Exit");
				Game.INSTANCE.stop();
				break;
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		Fonts.drawString(g, new Font("Optima", Font.BOLD, 72), Color.GRAY, Game.TITLE, 70);
		
		for(int i=0; i<options.length; i++) {
			if(i==currentSelection)
				options[i].setSelected(true);
			else
				options[i].setSelected(false);
			options[i].render(g);
		}
	}

	@Override
	public void exit() {}

	@Override
	public String getName() {
		return "menu";
	}

}
