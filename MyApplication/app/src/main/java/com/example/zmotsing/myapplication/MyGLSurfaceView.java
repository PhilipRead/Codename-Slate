package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import static com.example.zmotsing.myapplication.MyGLRenderer.addControlPoints;

/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class MyGLSurfaceView extends GLSurfaceView {

    Context mycontext;
    MyGLRenderer r = new MyGLRenderer();
    public MyGLSurfaceView(Context context)
    {
        super(context);
        r.setContext(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(1);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(r);
        mycontext = context;

        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    @Override public boolean onTouchEvent(MotionEvent e)
    {
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
        boolean keyboardevent = true;


        if(keyboardevent)
        {
            ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this, 0);
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg)
    {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            return true;
        }


        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getWindowToken(), 0);
        }

        return false;
    }



}