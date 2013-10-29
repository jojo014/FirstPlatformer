package main;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL15.*;

public class Game {

	public static Player player;
	public static Enemy enemy;

	public static ArrayList<Coin> coins;
	public static ArrayList<Coin> removedCoins;

	public static void main(String[] args) throws Exception {
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.create();

		player = new Player();
		enemy = new Enemy();
		coins = new ArrayList<Coin>(0);
		removedCoins = new ArrayList<Coin>(0);

		while (!Display.isCloseRequested()) {

			setCamera();
			drawScene();
			player.draw();
			enemy.draw();
			for (Coin c : coins) {
				c.draw();
			}
			removeCoins();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
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

	// Create the handle for the VBO
	public static int vertexBufferObject = glGenBuffers();
	
	public static void VBO() {
		// Create a new FloatBuffer (complex array of floats) with the capacity
		// of the length of the points * 3 (because we have 3 vertices per
		// point)
		FloatBuffer vertexArray = BufferUtils.createFloatBuffer(4);
		// Iterate over all the points and store them in the FloatBuffer
		vertexArray.put(new float[] { (float) player.x, (float) player.y, (float) (player.x + player.w), (float) (player.y + player.h) });
		// Make the buffer read-able for OpenGL
		vertexArray.flip();

		// Bind the VBO for usage (in this case: storing information)
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
		// Store all the contents of the FloatBuffer in the VBO.
		glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
		// Un-bind the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private static void setCamera() {
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 0, 480, -1, 1);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

}
