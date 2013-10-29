package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.input.Keyboard;

public class Player {

	public double x, y, w, h, xs, ys;
	public boolean jumpPressed, jumpWasPressed, dead;
	public int jumpsLeft, score = 0;

	public Player() {
		x = 100;
		y = 66;
		w = h = 16;
		ys = -2;
	}

	private void logic() {
		x += xs;
		y += ys;
		if (x > 650) x = -10;
		if (x < -10) x = 650;
		ys -= .4;

		if (dead) {
			if (y < -800) {
				dead = false;
			}
			return;
		}

		if (y <= 32) {
			ys = 0;
			y = 32;
			jumpsLeft = 2;

			if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && xs < 0) xs = xs * 0.9;
			if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && xs > 0) xs = xs * 0.9;
		}

		if (jumpPressed && !jumpWasPressed && jumpsLeft-- > 0) ys = 7;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) xs = Math.max(-3, xs - 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) xs = Math.min(3, xs + 1);

		jumpWasPressed = jumpPressed;
		jumpPressed = Keyboard.isKeyDown(Keyboard.KEY_UP);

		// Collision detection

		if (Math.abs(x - Game.enemy.x) > 16 || Math.abs(y - Game.enemy.y) > 16) return;
		if (ys < 0) {
			Game.enemy.kill();
		} else {
			kill();
		}
	}

	private void kill() {
		 dead = true;
		 ys = 12;
		 xs = xs * .5;
		 score -= 5;
		 System.out.println(score);
	}

	public void draw() {
		logic();

		glLoadIdentity();
		glPushMatrix();
		glTranslated(x, y, 0);

		/*glBegin(GL_QUADS);
		{
			glColor3d(1, 0, 0);
			glVertex2d(-w/2, 0);

			glColor3d(0, 1, 0);
			glVertex2d(w/2, 0);

			glColor3d(0, 0, 1);
			glVertex2d(w/2, h);

			glColor3d(1, 1, 0);
			glVertex2d(-w/2, h);
		}
		glEnd();*/
		
		  // Enable Vertex Arrays (VBOs)
        glEnableClientState(GL_VERTEX_ARRAY);
        // Tell OpenGL to use our VBO
        glBindBuffer(GL_ARRAY_BUFFER, Game.vertexBufferObject);
        // Tell OpenGL to find the data in the bound VBO with 3 co
        // mponents (xyz) and with the type float.
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        // Tell OpenGL to draw the data supplied by the pointer method
        // as points.length amount of points
        glDrawArrays(GL_QUADS, 0, 4);
        // Unbind the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        // Disable Vertex Arrays (VBOs)
        glDisableClientState(GL_VERTEX_ARRAY);

		glPopMatrix();
	}

}
