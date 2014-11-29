package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.graphics.Camera;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.Build;
import android.util.Log;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLRenderer implements GLSurfaceView.Renderer {


    /**
     * Keeps track of the Projection matrix calculated on the last draw frame
     */
    private float[] lastProjectionMat = null;

    /**
     * Keeps track of the model view matrix calculated on the last frame
     */
    private float[] lastModelViewMat = null;


    private final String vertexShaderCode = "attribute vec4 vPosition; \n"
            + "void main(){              \n" + " gl_Position = vPosition; \n"
            + "}                         \n";

    private final String fragmentShaderCode = "precision mediump float;  \n"
            + "void main(){              \n"
            + " gl_FragColor = vec4 (0.63671875, 0.76953125, 0.22265625, 1.0); \n"
            + "}                         \n";

    private int mProgram;
    private int maPositionHandle;

    static LineStrip linestrip;
    static List<Coord> controlPoints = new ArrayList<>();

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | //
                GL10.GL_DEPTH_BUFFER_BIT);

        // Draw our square.
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);
        if(linestrip != null) {
            linestrip.draw(gl); // ( NEW )

        }
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.
         * microedition.khronos.opengles.GL10, int, int)
     */


    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();

    }
    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig eglConfig) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);


        this.lastProjectionMat = new float[16];
        this.lastModelViewMat = new float[16];
        controlPoints.add(new Coord(-0.6f,  -0.6f));
        controlPoints.add(new Coord(0.6f,  -0.6f));
        controlPoints.add(new Coord(0.6f,  0.6f));
        controlPoints.add(new Coord(0.6f,  1f));
        controlPoints.add(new Coord(0.2f,  -1f));
        linestrip = new LineStrip(Spline.interpolate(controlPoints,60,CatmullRomType.Chordal));
    }
    public static void addControlPoints(float x, float y)
    {
        controlPoints.add(new Coord(x,y));
        if(controlPoints.size() > 2) {
            linestrip = new LineStrip(Spline.interpolate(controlPoints, 60, CatmullRomType.Chordal));
        }
    }
    /**
     * Calculates the transform from screen coordinate
     * system to world coordinate system coordinates
     * for a specific point, given a camera position.
     *
     * @param touch Coord point of screen touch, the
    actual position on physical screen (ej: 160, 240)
     * @return position in WCS.
     */
    public Coord GetWorldCoords(Coord touch)
    {
        Coord worldPos;

        // SCREEN height & width (ej: 320 x 480)
        float screenW = MyActivity.width;
        float screenH = MyActivity.height;
        float oglTouchY = screenH - touch.Y;

       /* Transform the screen point to clip space in ogl (-1,1) */
        float glWidth = (float) ((touch.X) * 6f / screenW - 3);
        float glHeight = (float) ((oglTouchY) * 3.2f / screenH -1.5);

        worldPos = new Coord(glWidth, glHeight);

        return worldPos;
    }
}