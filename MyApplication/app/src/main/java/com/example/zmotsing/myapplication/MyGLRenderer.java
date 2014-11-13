package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLRenderer implements GLSurfaceView.Renderer {

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Triangle mTriangle = new Triangle();
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eglConfig) {

    }
}