package com.example.zmotsing.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by acowdrey on 12/9/14.
 */
public class Sprite {
    private int resID;                  //the id of the resource bitmap
    private FloatBuffer vertexBuffer;	// buffer holding the vertices
    private float width = 0.2f;
    private float height = 0.1333f;
    public float X = 1.5f;
    public float Y = 0.2f;
    private float vertices[] = {
            0.0f, 0.0f, 0.0f,		// V1 - bottom left
            0.0f, 0.0f, 0.0f,		// V2 - top left
            0.0f, 0.0f, 0.0f,		// V3 - bottom right
            0.0f, 0.0f, 0.0f			// V4 - top right
    };

    private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
    private float texture[] = {
            // Mapping coordinates for the vertices
            0.0f, 1.0f,		// top left		(V2)
            0.0f, 0.0f,		// bottom left	(V1)
            1.0f, 1.0f,		// top right	(V4)
            1.0f, 0.0f		// bottom right	(V3)
    };


    public Sprite(int resourceID, float x,float y) {
        SetupSprite(resourceID,x,y);
    }

    public Sprite(int resourceID, float x,float y,float w, float h) {
        width = w;
        height = h;
        SetupSprite(resourceID,x,y);
    }

    public Sprite(int resourceID, float x,float y,float w, float h, float textureMap[]) {
        width = w;
        height = h;
        texture = textureMap;
        SetupSprite(resourceID,x,y);
    }

    public void SetupSprite(int resourceID, float x,float y)
    {

        resID = resourceID;
        X = x;
        Y = y;
        vertices[0] = -width + X; vertices[1] = -height + Y; vertices[2] =  0.0f;   // V1 - bottom left
        vertices[3] = -width + X; vertices[4] =  height + Y; vertices[5] =  0.0f;   // V2 - top left
        vertices[6] =  width + X; vertices[7] = -height + Y; vertices[8] =  0.0f;   // V3 - bottom right
        vertices[9] =  width + X; vertices[10]=  height + Y; vertices[11]=  0.0f;   // V4 - top right
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuffer.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);
    }

    /** The draw method for the square with the GL context */
    public void draw(GL10 gl) {
        // bind the previously generated texture
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable (GL10.GL_BLEND);
        gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        // Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);

        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    /** The texture pointer */
    private int[] textures = new int[1];

    public void loadGLTexture(GL10 gl, Context context) {
        // loading texture
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resID);

        // generate one texture pointer
        gl.glGenTextures(1, textures, 0);
        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // create nearest filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Use Android GLUtils to specify a two-dimensional texture image from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // Clean up
        bitmap.recycle();
    }

}
