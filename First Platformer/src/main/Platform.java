package main;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;

public class Platform {

	public double x, y, w, h;

	private VBO vbo;
	private FloatBuffer fbV, fbC;
	private int vboVertexHandle = glGenBuffers();
	private int vboColorHandle = glGenBuffers();

	public Platform(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		fbV = BufferUtils.createFloatBuffer(4*2);
		fbV.put(new float[] {(float) x, 0, (float) (x+w), 0, (float) (x+w), (float) h, (float) x, (float) h});
		fbV.flip();
		
		fbC = BufferUtils.createFloatBuffer(3*4);
		fbC.put(new float[] {0, 0.2f, 1f, 0, 0.2f, 1f, 0, 0.2f, 1f, 0, 0.2f, 1f});
		fbC.flip();
		
		vbo = new VBO();
		vbo.setUp(fbV, vboVertexHandle, fbC, vboColorHandle);
	}
	
	public void draw(){
		vbo.render(vboVertexHandle, vboColorHandle, x, y);
	}

	public void exit() {
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
	}

}
