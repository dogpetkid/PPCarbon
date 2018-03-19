package dpk;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import input.KeyInput;
import input.MouseInput;
import render.Texture;
import render.sprites.SpriteSheet;
import states.StateManager;
import states.rooms.room1;

/**
 * Main game.
 */
public class Game extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Carbon";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 384;
//	public static final int HEIGHT = WIDTH / 16 * 9;
	
	public static Game INSTANCE;
	private boolean running;
	public static boolean debug;
	public static boolean debugPrint; // tells objects when they are allowed to print their debug
	
	public static SpriteSheet sheet1;
	
	private StateManager sm;
	
	
	public Game() {
		sheet1 = new SpriteSheet(new Texture("sheet1"), 32);
		
		INSTANCE = this;
		KeyInput ki = new KeyInput();
		addKeyListener(ki);
		MouseInput mi = new MouseInput();
		addMouseListener(mi);
		addMouseMotionListener(mi);
		sm = new StateManager();
		
		sm.addState(new MenuState());
		sm.addState(new room1());
	}
	
	protected void start() {
		if(running) return;
		running = true;
		new Thread(this, "CarbainMain-Thread").start();
	}
	
	public void stop() {
		System.exit(0);
	}
	
	@Override
	public void run() {
		
		requestFocus();
		
		double target = 60.0; // target fps & tps
		double nsPerTick = 1000000000.0 / target; // calculate nanoseconds per tick
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double unprocessed = 0.0; // track when to process
		int fps = 0; // Frames per Second
		int tps = 0; // Ticks per Second
		boolean canRender = false; // track when to render
		
		while(running) {
			
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			// Handles tick cycle
			if(unprocessed >= 1.0) {
				tick();
				KeyInput.update();
				MouseInput.update();
				unprocessed--;
				tps++;
				canRender = true;
			}else canRender = false;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(canRender) {
				render();
				fps++;
			}
			
			// Debug
			if(System.currentTimeMillis() - 1000 > timer) {
				timer+= 1000;
				if(debug) {
					System.out.printf("fps: %d ~ tps %d\n", fps, tps);
					System.out.printf("X: %d ~ Y %d\n", MouseInput.getX(), MouseInput.getY());
					// TODO: fix the debugPrint: it only triggers once and then stops working
					debugPrint = true;
				}
				fps = 0;
				tps = 0;
			} else {debugPrint = false;}
		}
		
		System.exit(0);
	}
	
	private void tick() {
		if(KeyInput.wasPressed(KeyEvent.VK_F11)) {
			debug = !debug;
			System.out.println("Debug toggled: "+debug);
		}
		
		if(KeyInput.wasPressed(KeyEvent.VK_END)) this.stop();
		
		sm.tick();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		// creates a buffer strategy if none exists
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g; 
		g2d.translate(0, 0);
		
		g2d.setColor(new Color(192,192,192));
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		sm.render(g2d);
		
		g.dispose();
		bs.show();
	}

}
