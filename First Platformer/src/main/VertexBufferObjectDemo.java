package main;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObjectDemo {

	final static int amountOfVertices = 4;
	final static int vertexSize = 2;
	final static int colorSize = 3;
	static int x = 100, y = 6;

//	public static void main(String[] args) {
//		try {
//			Display.setDisplayMode(new DisplayMode(640, 480));
//			Display.setTitle("Vertex Buffer Object Demo");
//			Display.create();
//		} catch (LWJGLException e) {
//			e.printStackTrace();
//			Display.destroy();
//			System.exit(1);
//		}
//
//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();
//		glOrtho(0, 640, 0, 480, 1, -1);
//		glMatrixMode(GL_MODELVIEW);
//		glLoadIdentity();
//
//		FloatBuffer vertexData = BufferUtils.createFloatBuffer(amountOfVertices * vertexSize);
//		vertexData.put(new float[] { -8f, 0f, 8f, 0f, 8f, 16f, -8f, 16f});
//		vertexData.flip();
//
//		FloatBuffer colorData = BufferUtils.createFloatBuffer(amountOfVertices * colorSize);
//		colorData.put(new float[] { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0 });
//		colorData.flip();
//
//		int vboVertexHandle = glGenBuffers();
//		int vboColorHandle = glGenBuffers();
//
//		setUp(vertexData, vboVertexHandle, colorData, vboColorHandle);
//
//		while (!Display.isCloseRequested()) {
//			glClear(GL_COLOR_BUFFER_BIT);
//
//			render(vboVertexHandle, vboColorHandle, x, y);
//			input();
//
//			Display.update();
//			Display.sync(60);
//		}
//
//		glDeleteBuffers(vboVertexHandle);
//		glDeleteBuffers(vboColorHandle);
//
//		Display.destroy();
//		System.exit(0);
//	}
//
//	private static void input() {
//		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
//			y++;
//		}
//	}

	public static void setUp(FloatBuffer vertexData, int vboVertexHandle, FloatBuffer colorData, int vboColorHandle) {
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
		glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

	}

	public static void render(int vboVertexHandle, int vboColorHandle, double x, double y) {
		glLoadIdentity();
		glPushMatrix();
		glTranslated(x, y, 0);
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glVertexPointer(vertexSize, GL_FLOAT, 0, 0L);

		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
		glColorPointer(colorSize, GL_FLOAT, 0, 0L);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glDrawArrays(GL_QUADS, 0, amountOfVertices);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		glPopMatrix();
	}
}