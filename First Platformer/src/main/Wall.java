package main;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Wall {
	public double x, y, w, h;

	private VBO vbo;
	private FloatBuffer fbV, fbC;
	private int vboVertexHandle = glGenBuffers();
	private int vboColorHandle = glGenBuffers();
	
	public Wall(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		fbV = BufferUtils.createFloatBuffer(4 * 2);
		fbV.put(new float[] { 0, 0, (float) (w), 0, (float) (w), (float) h, (float) 0, (float) h });
		fbV.flip();

		fbC = BufferUtils.createFloatBuffer(3 * 4);
		fbC.put(new float[] { 0.1f, 0.1f, 1f, 0.1f, 0.1f, 1f, 0.1f, 0.1f, 1f, 0.1f, 0.1f, 1f });
		fbC.flip();

		vbo = new VBO();
		vbo.setUp(fbV, vboVertexHandle, fbC, vboColorHandle);
	}
	
	public void draw(){
		vbo.render(vboVertexHandle, vboColorHandle, x, y);
	}
	
	public void exit(){
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
	}
	
}
