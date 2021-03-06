package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.zmotsing.myapplication.Backend.BackendLogic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;

import static com.example.zmotsing.myapplication.MyGLRenderer.*;

/**
 * Created by acowdrey on 11/12/14.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLSurfaceView extends GLSurfaceView {

    Context mycontext;
    MyGLRenderer r = new MyGLRenderer();

    AlertDialog.Builder builder;
    public TextView textView;

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
        //BackendLogic.backendInitialize();

        builder = new AlertDialog.Builder(mycontext);
        int sHeight = mycontext.getResources().getDisplayMetrics().heightPixels - 60;
        int sWidth = mycontext.getResources().getDisplayMetrics().widthPixels / 4;

        textView = new TextView(mycontext);
        textView.setHeight(sHeight/2 - 20);
        textView.setCursorVisible(true);
        textView.setBackgroundColor(Color.BLACK);
        textView.setPadding(2,0,2,0);
        textView.setTextColor(Color.GREEN);
        textView.setTextSize(10);
        textView.setVerticalScrollBarEnabled(true);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        TypedArray a = textView.getContext().getTheme().obtainStyledAttributes(new int[0]);
        try {
            Method initializeScrollbars = android.view.View.class.getDeclaredMethod("initializeScrollbars", TypedArray.class);
            initializeScrollbars.invoke(this, a);

        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        a.recycle();




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

                                boolean tempIsNum = inputBuffer.matches("-?\\d+(\\.\\d+)?|-?\\.\\d+");
                                BackendLogic.updateBackendNode(Tn.curNode.getID(), inputBuffer, tempIsNum);


                                Tn.start();
                                return true;
                            }
                            else if(keyCode == KeyEvent.KEYCODE_DEL)
                            {
                                int inputLength = inputBuffer.length();

                                if(inputLength > 0)
                                {
                                    inputBuffer = inputBuffer.substring(0, inputLength - 1);

                                    CharSequence tempTxt = textView.getText();
                                    CharSequence newTxt = tempTxt.subSequence(0, tempTxt.length() - 1);
                                    textView.setText(newTxt);
                                }

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


    public static boolean swipeMode = true;
    public static boolean nodeMoveMode = false;
    boolean pinchMode = false;


    public static Timer moveNodeTimer;

    String inputBuffer;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Coord c = new Coord(e.getX(), e.getY());
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                actionMovedCoord = c;

                if(swipeMode) {
                    actionMoved = true;
                    if (moveNodeTimer != null) {
                        if (Math.abs(c.X - actionDownCoord.X) > 5
                                || Math.abs(c.Y - actionDownCoord.Y) > 5) {
                            moveNodeTimer.cancel();
                            moveNodeTimer.purge();
                        }
                    }
                }
                else if(nodeMoveMode) {
                    nodeMoved = true;
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
                if(moveNodeTimer != null) {
                    moveNodeTimer.cancel();
                    moveNodeTimer.purge();
                    nodeIsTapped = true;
                }
                if(nodeMoveMode) {
                    nodeMovedFinished = true;
                }
                nodeMoveMode = false;
                swipeMode = true;
        }

        return true;
    }

    public static int numlines = 0;

    public void getInput() {
        textView.append("> ");

        inputBuffer = "";
        ((InputMethodManager) mycontext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        Log.w("INFO", textView.getScrollBarSize() + "");
        numlines++;
        if(numlines > 9) {
            textView.setScrollY(numlines * 11 - 108);
        }
    }

    public void getOutput(int nodeID) {
        String curOutput = BackendLogic.getBackendVal(nodeID);
        textView.append(curOutput + "\n");
        numlines++;
        if(numlines > 9) {
            textView.setScrollY(numlines * 11 - 108);
        }


        Tn.start();
    }

    public boolean getIfValue(int nodeID) {
        BackendLogic.calculateIf(nodeID);
        return BackendLogic.getIfBool(nodeID);
    }

    public boolean caluculateMath(int nodeID) {
        return BackendLogic.calculateMath(nodeID);
    }

    public void performSet(int nodeID) {
        BackendLogic.doSet(nodeID);
    }
}