package main;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

public class Player {

	public double x, y, w, h, xs, ys;
	public boolean jumpPressed, jumpWasPressed, dead, standing = false;;
	public int jumpsLeft, score = 0;

	private FloatBuffer fbV, fbC;
	private VBO vbo;
	public Platform standingOn;

	int vboVertexHandle = glGenBuffers();
	int vboColorHandle = glGenBuffers();

	public Player() {
		x = Game.xOff = 100;
		y = 66;
		h = 16;
		w = h / 2;
		ys = -2;

		fbV = BufferUtils.createFloatBuffer(4 * 2);
		fbV.put(new float[] { -8f, 0f, 8f, 0f, 8f, 16f, -8f, 16f });
		fbV.flip();

		fbC = BufferUtils.createFloatBuffer(4 * 3);
		fbC.put(new float[] { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0 });
		fbC.flip();

		vbo = new VBO();
		vbo.setUp(fbV, vboVertexHandle, fbC, vboColorHandle);

	}

	public void update() {
		x += xs;
		y += ys;
		ys -= .4;

		if (dead) {
			if (y < -800) {
				dead = false;
			}
			return;
		}

		Game.xOff = x - 200;
		
		if (ys < 0) { // is moving down
			for (Platform p : Game.platforms) {
				if (x > p.x && x < (p.x + p.w)) {
					if ((y == p.y) || (y < (p.y + p.h) && y > p.y)) {
						standing = true;
						y = p.y + p.h;
						p.landed();
						standingOn = p;
						break;
					}
				} else {
					standing = false;
					standingOn = null;
				}
			}
		}

		if(standingOn != null)
		
		if (y <= 32) {
			standing = true;
			y = 32;
		}

		if (standing) {
			ys = 0;
			jumpsLeft = 2;

			if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && xs < 0)
				xs = xs * 0.4;
			if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && xs > 0)
				xs = xs * 0.4;
		}

		if (jumpPressed && !jumpWasPressed && jumpsLeft-- > 0) {
			ys = 7;
			standing = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			xs = Math.max(-3, xs - 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			xs = Math.min(3, xs + 1);

		jumpWasPressed = jumpPressed;
		jumpPressed = Keyboard.isKeyDown(Keyboard.KEY_UP);

		// Collision detection

		if (Math.abs(x - Game.enemy.x) > 16 || Math.abs(y - Game.enemy.y) > 16)
			return;
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
	//	vbo.render(vboVertexHandle, vboColorHandle, x, y);
		vbo.staticRender(vboVertexHandle, vboColorHandle, x, y);
	}

	public void exit() {
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
	}

}
