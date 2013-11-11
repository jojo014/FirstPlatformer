package main;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;

public class Platform {

	public double x, y, w, h;

	private VBO vbo, vboLanded;
	private FloatBuffer fbV, fbC, fbLanded;
	private int vboVertexHandle = glGenBuffers();
	private int vboColorHandle = glGenBuffers();
	private int vboColorLanded = glGenBuffers();
	private boolean landed;

	public Platform(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		fbV = BufferUtils.createFloatBuffer(4 * 2);
		fbV.put(new float[] { 0, 0, (float) (w), 0, (float) (w), (float) h, (float) 0, (float) h });
		fbV.flip();

		fbC = BufferUtils.createFloatBuffer(3 * 4);
		fbC.put(new float[] { 0, 0.2f, 1f, 0, 0.2f, 1f, 0, 0.2f, 1f, 0, 0.2f, 1f });
		fbC.flip();

		fbLanded = BufferUtils.createFloatBuffer(3 * 4);
		fbLanded.put(new float[] { 0, 1f, 0.2f, 0, 1f, 0.2f, 0, 1f, 0.2f, 0, 1f, 0.2f });
		fbLanded.flip();

		vbo = new VBO();
		vbo.setUp(fbV, vboVertexHandle, fbC, vboColorHandle);

		vboLanded = new VBO();
		vboLanded.setUp(fbV, vboVertexHandle, fbLanded, vboColorLanded);
	}

	public void landed() {
		if (!landed) {
			landed = true;
			Player.score += 10;
			System.out.println(Player.score);
		}
	}

	public void draw() {
		if (landed) {
			vboLanded.render(vboVertexHandle, vboColorLanded, x, y);
		} else {
			vbo.render(vboVertexHandle, vboColorHandle, x, y);
		}
	}

	public void exit() {
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
		glDeleteBuffers(vboColorLanded);
	}

}
