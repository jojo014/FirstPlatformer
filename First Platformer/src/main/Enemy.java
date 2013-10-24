package main;

import static org.lwjgl.opengl.GL11.*;

public class Enemy {

	public double x, y, xs;

	public Enemy() {
		x = 200;
		y = 32;
		 xs = 2;
	}

	private void logic() {
		x += xs;
		if (x > 650) x = -10;

	}

	public void draw() {
		logic();

		glLoadIdentity();
		glPushMatrix();
		glTranslated(x, y, 0);

		glBegin(GL_QUADS);
		{
			glColor3d(0.7, 0, 0);

			glVertex2d(-8, 0);
			glVertex2d(8, 0);
			glVertex2d(8, 16);
			glVertex2d(-8, 16);
		}
		glEnd();

		glPopMatrix();
	}

	public void kill() {
		double coin = Math.random() * 5;
		for (int i = 0; i < coin; i++) {
			Game.coins.add(new Coin(x, y));
		}
		x = Math.random() * 640;
	}

}
