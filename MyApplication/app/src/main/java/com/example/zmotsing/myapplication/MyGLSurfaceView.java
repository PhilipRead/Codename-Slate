package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;

/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new MyGLRenderer());
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}