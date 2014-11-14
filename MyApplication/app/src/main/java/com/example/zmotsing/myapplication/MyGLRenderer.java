package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLRenderer implements GLSurfaceView.Renderer {


    private FloatBuffer triangleVB;

    private final String vertexShaderCode = "attribute vec4 vPosition; \n"+ "void main(){              \n" + " gl_Position = vPosition; \n"+ "}                         \n";

    private final String fragmentShaderCode = "precision mediump float;  \n"+ "void main(){              \n" + " gl_FragColor = vec4 (0.63671875, 0.76953125, 0.22265625, 1.0); \n" + "}                         \n";

    private int mProgram;
    private float fun=0;
    private int bobble = 1;
    private int mPositionHandle;

    private int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    private float[] mRotationMatrix = new float[16];
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12, triangleVB);

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        fun+= .03 *bobble;
        if(Math.abs(fun) > .5)
        {
            bobble *= -1;
        }
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        initShapes();
    }


    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eglConfig) {
        gl10.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        initShapes();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    }

    private void initShapes() {
        float[] triangleCoords = new float[]{
                // X,Y,Z
                -0.5f, -0.25f, 0, 0.5f, -0.25f, 0, 0.0f + fun, 0.559016994f, 0};
        ByteBuffer vbb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        triangleVB = vbb.asFloatBuffer();
        triangleVB.put(triangleCoords);
        triangleVB.position(0);
    }


}