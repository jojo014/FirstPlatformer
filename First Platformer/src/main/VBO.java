package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class VBO {

	static int VBOid, colorBufferID, indexBufferID, vertexSize;
	final static int amountOfVertices = 4;
	// final static int vertexSize = 3;
	final static int colorSize = 3;

	public VBO(FloatBuffer bufferV, FloatBuffer bufferC, int vSize) {
		// VBOid = createVBOID();
		// vertexSize = vSize;
		// staticBufferData(VBOid, bufferV);
		// colorBufferID = createVBOID();
		// staticBufferData(colorBufferID, bufferC);

		VBOid = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBOid);
		glBufferData(GL_ARRAY_BUFFER, bufferV, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		colorBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
		glBufferData(GL_ARRAY_BUFFER, bufferC, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void render(double x, double y) {
		glLoadIdentity();
		glPushMatrix();
		glTranslated(x, y, 0);

		glBindBuffer(GL_ARRAY_BUFFER, VBOid);
		glVertexPointer(vertexSize, GL_FLOAT, 0, 0L);

		glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
		glColorPointer(4, GL_FLOAT, 0, 0L);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glDrawArrays(GL_QUADS, 0, vertexSize);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		glPopMatrix();
	}

	public static int createVBOID() {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffers(buffer);
		return buffer.get(0);
	}

	public static void dynamicBufferData(int id, FloatBuffer buffer) {
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static void staticBufferData(int id, FloatBuffer buffer) {
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
