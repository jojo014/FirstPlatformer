package main;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Coin {

	public double x, y, w, h, xs, ys;
	public int timer;
	public boolean stop;
	
	private VBO vbo;
	private int vboVertexHandle = glGenBuffers();
	private int vboColorHandle = glGenBuffers();
	private FloatBuffer fbV, fbC;
	

	public Coin(double x2, double y2) {
		x = x2;
		y = y2;
		w = h = 8;
		xs = (Math.random() * 2 - 1) * 5;
		ys = 3 + Math.random() * 4;
		
		fbV = BufferUtils.createFloatBuffer(4*2);
		fbV.put(new float[] {-4, 0, 4, 0, 4, 8, -4, 8});
		fbV.flip();
		
	}

	public void update() {
		if (!stop) timer++;
		x += xs;
		y += ys;
		if (x > 650) x = -10;
		if (x < -10) x = 650;

		if (Math.abs(ys) < .3) ys = 0;

		if (y <= 32) {
			ys = -ys * 0.5;
			y = 32;
			xs = xs * 0.9;
		} else {
			ys -= .4;
		}

		if (timer < 5) return;
		else
			stop = true;

		if (Math.abs(x - Game.player.x) < 8 && Math.abs(y - Game.player.y) < 8) {
			Game.removedCoins.add(this);
			Game.player.score += 10;
			System.out.println(Game.player.score);
		}
	}

	public void draw() {
		glLoadIdentity();
		glPushMatrix();
		glTranslated(x, y, 0);

		glBegin(GL_QUADS);
		{
			glColor3d(1, 1, 0);
			glVertex2d(-w / 2, 0);
			glVertex2d(w / 2, 0);
			glVertex2d(w / 2, h);
			glVertex2d(-w / 2, h);
		}
		glEnd();

		glPopMatrix();
	}
	
	
	public void exit(){
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
	}

}
