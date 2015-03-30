package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import static com.example.zmotsing.myapplication.MyGLRenderer.*;

/**
 * Created by acowdrey on 11/12/14.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
class MyGLSurfaceView extends GLSurfaceView {

    Context mycontext;
    MyGLRenderer r = new MyGLRenderer();

    public MyGLSurfaceView(Context context) {
        super(context);
        r.setContext(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(11);
        // Set the Renderer for drawing on the GLSurfaceView

        setRenderer(r);
        mycontext = context;
        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    boolean action_flag = false;
    boolean swipeMode = true;
    boolean pinchMode = false;
    boolean inputMode = false;



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Coord c = new Coord(e.getX(), e.getY());
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                actionMovedCoord = c;

                if(swipeMode) {
                    actionMoved = true;
                }
                else if(pinchMode) {
                    pointerMovedCoord = new Coord(e.getX(1), e.getY(1));
                    pinchMoved = true;
                }

                return true;
            case MotionEvent.ACTION_DOWN:
                actionDownCoord = c;
                action_flag = true;

                actionDown = true;

                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
                actionMovedCoord = c;
                pointerDownCoord = new Coord(e.getX(1), e.getY(1));
                swipeMode = false;
                pinchMode = true;
                pointerDown = true;

                return true;
            case MotionEvent.ACTION_POINTER_UP:
                pinchMode = false;
                swipeMode = true;

                return true;
            case MotionEvent.ACTION_UP:
                if(action_flag) {
                    TouchEventCoord = c;
                    Touched = true;
                    addControlPoints(c.X, c.Y);
                    action_flag = false;
                }
        }
        boolean keyboardevent = true;

        if (keyboardevent) {
            //((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this,InputMethodManager.RESULT_SHOWN);
            getInput();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {

        if(inputMode)
        {
            if(keyCode >= 29 && keyCode <= 54) //Letter
            {
                inputTxtToLoad.add("" + (char)msg.getUnicodeChar(1));
               // Log.w("LETTER", "" + (char)msg.getUnicodeChar(1));
            }
            else if(keyCode >= 7 && keyCode <= 16 && msg.getMetaState() == 0) //Number
            {
                Log.w("NUMBER", "" + msg.getNumber());
            }
        }

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


    public boolean getInput(){

        inputMode = true;
        String inputBuffer = "";
        inputTxt.clear();
        ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this, InputMethodManager.RESULT_SHOWN);

        //Enter key detected. Close keyboard.
         //inputTxtToLoad.add(new TextObject());
        return true;
    }
}