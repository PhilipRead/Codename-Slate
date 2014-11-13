package com.example.zmotsing.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;

public class MyActivity extends Activity {
//    float oldX;
//    float oldY;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my);
//
//        final ImageView imageView = (ImageView)findViewById(R.id.redSquare);
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        oldX = event.getX();
//                        oldY = event.getY();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        imageView.setX(event.getRawX() - oldX);
//                        imageView.setY(event.getRawY() - oldY);
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        return true;
//                }
//                return false;
//            }
//        });
//    }

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }
}