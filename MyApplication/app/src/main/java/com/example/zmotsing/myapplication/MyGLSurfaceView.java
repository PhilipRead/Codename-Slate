package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.view.MotionEvent;

import static com.example.zmotsing.myapplication.MyGLRenderer.addControlPoints;

/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class MyGLSurfaceView extends GLSurfaceView {

    MyGLRenderer r = new MyGLRenderer();
    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(1);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(r);
        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    @Override public boolean onTouchEvent(MotionEvent e) {
        Coord c =  r.GetWorldCoords(new Coord(e.getX(),e.getY()));
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                float dx = x - mPreviousX;
//                float dy = y - mPreviousY;
//                mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
//                mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
//                requestRender();
            case MotionEvent.ACTION_DOWN:
                addControlPoints(c.X,c.Y);
                //oldX = event.getX()
                //oldY = event.getY();
                return true;
        }
        return true;
    }
}