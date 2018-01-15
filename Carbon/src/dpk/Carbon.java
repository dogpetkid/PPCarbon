package dpk;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import render.Texture;

public class Carbon extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TITLE = "Carbon";
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 16 * 9;
	
	private boolean running;
	private Texture texture;
	
	public Carbon() {
		texture = new Texture("badcircle");
	}
	
	private void start() {
		if(running) return;
		running = true;
		new Thread(this, "CarbainMain-Thread").start();
	}
	
	@Override
	public void run() {
		
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
				unprocessed--;
				tps++;
				canRender = true;
			}else canRender = false;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(canRender) {
				render();
				fps++;
			}
			
			// Debug
			if(System.currentTimeMillis() - 1000 > timer) {
				timer+= 1000;
				System.out.printf("fps: %d ~ tps %d\n", fps, tps);
				fps = 0;
				tps = 0;
			}
		}
		
		System.exit(0);
	}
	
	public static void main(String[] args) {
		Carbon game = new Carbon();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	private void tick() {}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		// creates a buffer strategy if none exists
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		///////////////////////////////////
		
		// Draw dummy frame
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		texture.render(g, 100, 100);
		
		///////////////////////////////////
		
		g.dispose();
		bs.show();
	}

}
