package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL12.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class VBO {

	static int VBOid, colorBufferID, indexBufferID, vertexSize;

	public VBO(FloatBuffer bufferV, FloatBuffer bufferC, int vSize) {
		VBOid = createVBOID();
		vertexSize = vSize;
		dynamicBufferData(VBOid, bufferV);
		colorBufferID = createVBOID();
		staticBufferData(colorBufferID, bufferC);
	}

	public void reBuffer(FloatBuffer bufferV) {
		dynamicBufferData(VBOid, bufferV);
	}

	public void render(double x, double y) {
		glPushMatrix();
		glLoadIdentity();
		glTranslated(x, y, 0);

		glBindBuffer(GL_ARRAY_BUFFER, VBOid);
		glVertexPointer(vertexSize, GL_FLOAT, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
		glColorPointer(3, GL_FLOAT, 0, 0);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);

		glDrawArrays(GL_QUADS, 0, vertexSize); // If you are not using IBOs

		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		// If you are using IBOs:
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferID);

		// glDrawElements(GL_TRIANGLES, numberIndices, GL_UNSIGNED_INT, 0);

		// The alternate glDrawElements.
		// GL12.glDrawRangeElements(GL_TRIANGLES, 0, maxIndex, numberIndices,
		// GL_UNSIGNED_INT, 0);

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
