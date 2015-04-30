package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zmotsing.myapplication.Backend.BackendLogic;

import static com.example.zmotsing.myapplication.MyGLRenderer.*;

/**
 * Created by acowdrey on 11/12/14.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLSurfaceView extends GLSurfaceView {

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
        r.surfaceview = this;
        mycontext = context;
        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        BackendLogic.backendInitialize();

        AlertDialog.Builder builder = new AlertDialog.Builder(mycontext);
        final TextView textView = new TextView(mycontext);
        int sHeight = mycontext.getResources().getDisplayMetrics().heightPixels - 60;
        int sWidth = mycontext.getResources().getDisplayMetrics().widthPixels / 4;

        textView.setHeight(sHeight/2 - 20);
        textView.setCursorVisible(true);
        textView.setBackgroundColor(Color.BLACK);
        textView.setPadding(2,0,2,0);
        textView.setTextColor(Color.GREEN);
        textView.setTextSize(10);

        builder.setCancelable(false)
                .setView(textView)
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent msg) {
                        if(msg.getAction() == KeyEvent.ACTION_UP)
                        {
                            if (keyCode == KeyEvent.KEYCODE_ENTER)
                            {
                                textView.append("\n");
                                ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                                BackendLogic.updateRegister(inputBuffer);

                                Tn.start();
                                return true;
                            }

                            char tempChar = (char) msg.getUnicodeChar();
                            textView.append(tempChar + "");
                            inputBuffer += tempChar;
                            return true;
                        }
                        return false;
                    }
                });

        AlertDialog alert = builder.create();


        alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alert.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        alert.show();
        alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.width = sWidth;
        wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        alert.getWindow().setAttributes(wmlp);
    }


    boolean swipeMode = true;
    boolean pinchMode = false;


    String inputBuffer;

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
                TouchedDown = true;
                TouchDownCoord = c;
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
                if(action_flag)
                {
                    TouchEventCoord = c;
                    Touched = true;
                    if( curPressed == null) {
                        addControlPoints(c.X, c.Y);
                    }
                    action_flag = false;
                    curPressed = null;
                }
        }

        return true;
    }

    public void getInput() {

        inputBuffer = "";
        inputTxt.clear();
        ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public void getOutput() {


        String curOutput = BackendLogic.printRegister();
        outputTxt.clear();
        outputTxtToLoad.add(curOutput);

        Tn.start();
    }


}