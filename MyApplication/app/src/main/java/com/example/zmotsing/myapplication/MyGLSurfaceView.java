package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
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
    }


    boolean swipeMode = true;
    boolean pinchMode = false;
    boolean inputMode = false;

    String inputBuffer;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TextView textView = new TextView(getContext());
        int sHeight = mycontext.getResources().getDisplayMetrics().heightPixels - 60;
        int sWidth = mycontext.getResources().getDisplayMetrics().widthPixels / 4;

        //textView.setMinWidth(0);
       // textView.setMinEms(0);
        textView.setHeight(sHeight);
       // textView.setWidth(10);

        //LinearLayout.LayoutParams tempParams = new LinearLayout.LayoutParams(10, sHeight);
        //textView.setLayoutParams(tempParams);
        //textView.setText("TEST");

        builder.setCancelable(false)
            .setView(textView);
                /*.setNegativeButton("I wish I was", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }*/
        AlertDialog alert = builder.create();

        alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alert.show();
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.width = sWidth;
        wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        //wmlp.height = sHeight;
        alert.getWindow().setAttributes(wmlp);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {

        if(inputMode)
        {
            if(keyCode >= 29 && keyCode <= 54) //Letter
            {
                char tempChar = (char)msg.getUnicodeChar(1);
                inputTxtToLoad.add("" + tempChar);
                inputBuffer += tempChar;
            }
            else if(keyCode >= 7 && keyCode <= 16 && msg.getMetaState() == 0) //Number
            {
                char tempNum = (char)msg.getUnicodeChar();
                Log.w("NUMBER", "" + tempNum);
                inputTxtToLoad.add("" + tempNum);
                inputBuffer += tempNum;
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
            if(inputMode)
            {
                inputMode = false;
                ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getWindowToken(), 0);

                BackendLogic.updateRegister(inputBuffer);

                Tn.start();
            }
            return true;
        }

        return false;
    }

    public void getInput() {

        inputMode = true;
        inputBuffer = "";
        inputTxt.clear();
        ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this, InputMethodManager.RESULT_SHOWN);
    }

    int testNum = 0;
    public void getOutput() {


        String curOutput = BackendLogic.printRegister();
        outputTxt.clear();
        outputTxtToLoad.add(curOutput);

        Tn.start();
    }


}