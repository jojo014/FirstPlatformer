package main;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game {

	public static Player player;
	public static Enemy enemy;

	public static ArrayList<Coin> coins;
	public static ArrayList<Coin> removedCoins;

	private static long lastFrame, lastFPS;
	private static int fps, ups;

	public static void loop() {
		lastFPS = getTime();
		getDelta();

		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;

		while (!Display.isCloseRequested()) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				ups++;
				delta--;
			}

			render();
			updateFPS();

			Display.update();
			Display.sync(60);
		}

		cleanUp();
	}

	public static void init() {
		player = new Player();
		enemy = new Enemy();
		coins = new ArrayList<Coin>(0);
		removedCoins = new ArrayList<Coin>(0);
	}

	private static void render() {
		setCamera();
		drawScene();
		player.draw();
		enemy.draw();
		for (Coin c : coins) {
			c.draw();
		}
	}

	private static void update() {
		player.update();
		enemy.update();
		for (Coin c : coins) {
			c.update();
		}
		removeCoins();
	}

	private static void removeCoins() {
		for (Coin c : removedCoins) {
			coins.remove(c);
			// removedCoins.remove(c);
		}

	}

	private static void drawScene() {
		glBegin(GL_QUADS);
		{
			// sky
			glColor3d(0.7, 0.8, 0.9);
			glVertex2d(0, 0);
			glVertex2d(640, 0);

			glColor3d(0.4, 0.5, 0.7);
			glVertex2d(640, 480);
			glVertex2d(0, 480);

			// ground
			glColor3d(0.5, 0.2, 0.0);
			glVertex2d(0, 0);
			glVertex2d(640, 0);

			glColor3d(0.2, 0.6, 0.0);
			glVertex2d(640, 32);
			glVertex2d(0, 32);
		}
		glEnd();
	}

	private static void setCamera() {
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 0, 480, 1, -1);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps + " || UPS: " + ups);
			fps = 0;
			ups = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static void cleanUp() {
		player.exit();
		enemy.exit();
		for(Coin c: coins){
			c.exit();
		}
		Display.destroy();
	}
	

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.err.println("Failed to init display");
		}

		init();
		loop();

	}

}
