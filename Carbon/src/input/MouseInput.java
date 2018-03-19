package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tracks mouse for events.
 * For buttons, use MouseEvent.<i>buttonCode</i>.
 */
public class MouseInput extends MouseAdapter{
	
	private static final int NUM_BUTTONS = 10;
	
	private static final boolean[] buttons = new boolean[NUM_BUTTONS];
	private static final boolean[] lastButtons = new boolean[NUM_BUTTONS];
	private static int x = -1;
	private static int y = -1;
	private static int lastX = x, lastY = y;
	private static boolean moving;
	
	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("Button press: " + e.getButton());
		buttons[e.getButton()] = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("Button rel: " + e.getButton());
		buttons[e.getButton()] = false;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		moving = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		moving = true;
	}
	
	/**
	 * Must occur after tick.
	 */
	public static void update() {
		for(int i=0; i<NUM_BUTTONS; i++) {
			lastButtons[i] = buttons[i];
		}
		
		if(x == lastX && y == lastY) moving = false;
		lastX=x;
		lastY=y;
	}
	
	public static boolean isDown(int buttonCode) {
		return buttons[buttonCode];
	}
	
	public static boolean wasPressed(int buttonCode) {
		return isDown(buttonCode) && !lastButtons[buttonCode];
	}
	
	public static boolean wasReleased(int buttonCode) {
		return !isDown(buttonCode) && lastButtons[buttonCode];
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static boolean isMoving() {
		return moving;
	}
	
	/**
	 * Checks if dragging for a certain button.
	 * @param buttonCode the button which dragging is checked
	 */
	public static boolean isDragging(int buttonCode) {
		return isMoving() && isDown(buttonCode);
	}

	/**
	 * @return True when the mouse has been acted upon in any way
	 */
	public static boolean isActing() {
		boolean acting = false;
		for(int i=0; i<NUM_BUTTONS; i++) {
			acting |= isDown(i);
			acting |= wasPressed(i);
			acting |= wasReleased(i);
		}
		acting |= isMoving();
		return acting;
	}

}
