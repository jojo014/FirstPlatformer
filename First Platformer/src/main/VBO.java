package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL12.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class VBO {

	static int VBOid, colorBufferID, indexBufferID, numberIndices;

	public VBO(FloatBuffer buffer, int numIndicies) {
		VBOid = createVBOID();
		vertexBufferData(VBOid, buffer);
		numberIndices = numIndicies;

	}

	public void render() {
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, VBOid);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glEnableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
		glColorPointer(4, GL_FLOAT, 0, 0);

		// If you are not using IBOs:
		glDrawArrays(GL_QUADS, 0, numberIndices);

		// If you are using IBOs:
		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferID);
		//glDrawElements(GL_TRIANGLES, numberIndices, GL_UNSIGNED_INT, 0);

		// The alternate glDrawElements.
		//GL12.glDrawRangeElements(GL_TRIANGLES, 0, maxIndex, numberIndices, GL_UNSIGNED_INT, 0);
	}

	public static int createVBOID() {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffers(buffer);
		return buffer.get(0);
	}

	public static void vertexBufferData(int id, FloatBuffer buffer) {
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
	}

	public static void indexBufferData(int id, IntBuffer buffer) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
	}

}
