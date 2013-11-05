package main;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Enemy {

	public double x, y, w, h, xs;

	// VBO Rendering Variables
	private FloatBuffer fbV, fbC;
	private VBO vbo;
	private int vboVertexHandle = glGenBuffers();
	private int vboColorHandle = glGenBuffers();

	public Enemy() {
		x = 200;
		y = 32;
		w = h = 16;
		xs = 2;

		fbV = BufferUtils.createFloatBuffer(4 * 2);
		fbV.put(new float[] { -8f, 0f, 8f, 0f, 8f, 16f, -8f, 16f });
		fbV.flip();

		fbC = BufferUtils.createFloatBuffer(4 * 3);
		fbC.put(new float[] { 0.7f, 0, 0, 0.7f, 0, 0, 0.7f, 0, 0, 0.7f, 0, 0 });
		fbC.flip();

		vbo = new VBO();
		vbo.setUp(fbV, vboVertexHandle, fbC, vboColorHandle);
	}

	public void update() {
		x += xs;
		if (x > 650)
			x = -10;
	}

	public void draw() {
		vbo.render(vboVertexHandle, vboColorHandle, x, y);
	}

	public void kill() {
		double coin = Math.random() * 5;
		for (int i = 0; i < coin; i++) {
			Game.coins.add(new Coin(x, y));
		}
		x = Math.random() * 640;
	}
	
	
	public void exit(){
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
	}

}
