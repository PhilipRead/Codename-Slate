package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.view.Window;
import android.view.WindowManager;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyActivity extends Activity {

    private GLSurfaceView mGLView;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        //mGLView.setRenderer(new MyGLRenderer());
        setContentView(mGLView);
    }
}