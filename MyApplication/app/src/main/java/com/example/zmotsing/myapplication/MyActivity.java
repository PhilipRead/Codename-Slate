package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import static com.example.zmotsing.myapplication.MyGLRenderer.addControlPoints;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyActivity extends Activity {
    float oldX;
    float oldY;
    DisplayMetrics displaymetrics = new DisplayMetrics();
    static float height;
    static float width;

    private GLSurfaceView mGLView;

    //load bitmap

    int i=0;
    //TextView tv;
    final Handler myHandler = new Handler();

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.


        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        mGLView = new MyGLSurfaceView(this);
        //mGLView.setRenderer(new MyGLRenderer());

        setContentView(mGLView);


    }


}