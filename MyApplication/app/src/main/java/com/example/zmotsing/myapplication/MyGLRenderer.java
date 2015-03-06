package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.example.zmotsing.myapplication.Buttons.IfButton;
import com.example.zmotsing.myapplication.Buttons.InputButton;
import com.example.zmotsing.myapplication.Buttons.OutputButton;
import com.example.zmotsing.myapplication.Nodes.OutputNode;
import com.example.zmotsing.myapplication.Nodes.TravelingNode;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11ExtensionPack;


/**
 * Created by acowdrey on 11/12/14.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLRenderer implements GLSurfaceView.Renderer {


    private int[] Textures = new int[1]; //textures pointers
    private int activeTexture = 0; //which texture is active (by index)

    int i = 0;
    final Handler myHandler = new Handler();
    private static Context myContext;
    private TravelingNode Tn;

    static int transX;
    static int transY;
    static int transZ = -4;

    /**
     * Constructor to set the handed over context
     */
    public MyGLRenderer() {


    }


    public static LineStrip linestrip;
    public static Coord TouchEventCoord;
    public static boolean Touched;
    static CopyOnWriteArrayList<Node> NodeList = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Node> ButtonList = new CopyOnWriteArrayList<>();
    public static TextManager textMngr = new TextManager(0.0f, 0.0f, 0.0f, 0.0f);


    static CopyOnWriteArrayList<Coord> controlPoints = new CopyOnWriteArrayList<Coord>();
    public static CopyOnWriteArrayList<Node> NodesToLoad = new CopyOnWriteArrayList<>();


    @Override
    public void onDrawFrame(GL10 gl) {

        //Load in all graphics for new nodes
        boolean RedrawLine = false;
        for (Node element : NodesToLoad) {
            //get the screen coordinate
            Coord ScreenCoord = element.getCoord();

            //get the drawable for this node
            Drawable d = myContext.getResources().getDrawable(element.drawableInt);

            //get the bottom right hand coordinate, scaling accordingly using scaling factor (scaling factor of one is actual size)
            Coord ScreenBRCoord = GetWorldCoords(gl, new Coord(ScreenCoord.X + element.scalingFactor * d.getIntrinsicWidth(), ScreenCoord.Y + element.scalingFactor * d.getIntrinsicHeight()));

            //put the screen coordinates into opengl coordinates
            Coord NewScreenCoord = GetWorldCoords(gl, ScreenCoord);

            //fin the opengl width and height
            element.setCoord(NewScreenCoord);
            element.Height = NewScreenCoord.Y - ScreenBRCoord.Y;
            element.Width = ScreenBRCoord.X - NewScreenCoord.X;
            element.setSprite();
            element.spr.loadGLTexture(gl, myContext);
            NodeList.add(element);

            //add to controlpoints for linestrip
            if (element.AddToLine == 1) {
                controlPoints.add(element.getCoord());
                RedrawLine = true;

            }

        }

        if (RedrawLine && controlPoints.size() > 2) {
            linestrip = new LineStrip(Spline.interpolate(controlPoints, 60, CatmullRomType.Chordal));
            //objectTouched();
        }
        NodesToLoad.clear();
        if (Touched) {
            Touched = false;
            objectTouched(GetWorldCoords(gl, TouchEventCoord));

        }

        // Clears the screen and depth buffer.

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | //
                GL10.GL_DEPTH_BUFFER_BIT);

        // Draw our square.
        // Translates 4 units into the screen.
//        square.draw(gl);

        gl.glTranslatef(transX, transY, transZ);


        for (TextObject element : textMngr.getTextList()) {
            element.spr.draw(gl);
        }

        for (Node element : ButtonList) {
            element.spr.draw(gl);
        }

        for (Node element : NodeList) {
            element.spr.draw(gl);
        }
        //NodeList.get(0).spr.draw(gl);
        if (linestrip != null) {
            linestrip.draw(gl); // ( NEW )

        }
        Tn.spr.draw(gl);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {

        //sets the viewport size
        gl.glViewport(0, 0, width, height);  //WHOLE SCREEN

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


        addControlPoints(200f, 200f);
        addControlPoints(100f, 200f);
        addControlPoints(200f, 100f);
        addControlPoints(250f, 50f);
        addControlPoints(400f, 26f);

        Tn = new TravelingNode(new Coord(-0.6f, -0.6f));
        float bs = 1.61f; // buttonspacing

//        ButtonList.add(new OutputButton(new Coord(-2.1f, 1.5f)));
//        ButtonList.add(new InputButton(new Coord(-.49f, 1.5f)));
//        ButtonList.add(new IfButton(new Coord(1.12f, 1.5f)));
        NodesToLoad.add(new OutputButton(new Coord(400f, 100f)));
        NodesToLoad.add(new InputButton(new Coord(400f, 200f)));
        NodesToLoad.add(new IfButton(new Coord(400f, 300f)));

        textMngr.addText("TEXTTEST");

        //nody = new NodeSprite();
        for (Node c : NodeList) {
            c.setSprite();
            c.spr.loadGLTexture(gl, myContext);
        }
        for (TextObject c : textMngr.getTextList()) {
            c.spr.loadGLTexture(gl, myContext);
        }
        for (Node c : ButtonList) {
            c.setSprite();
            c.spr.loadGLTexture(gl, myContext);
        }

        Tn.setSprite();
        Tn.spr.loadGLTexture(gl, myContext);
        //square.loadGLTexture(gl, myContext);


        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping
        gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    //Black Background
        gl.glClearDepthf(1.0f);                    //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);            //The Type Of Depth Testing To Do

        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


        for (Node c : NodeList) {
            controlPoints.add(c.getCoord());
        }

        linestrip = new LineStrip(Spline.interpolate(controlPoints, 60, CatmullRomType.Chordal));


        Timer myTimer = new Timer();

//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {AdvanceTravelingNode();}
//        }, 0, 10);


    }


    private void AdvanceTravelingNode() {
        i++;
        //tv.setText(String.valueOf(i));
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            Tn.action();
        }
    };

    public static void addControlPoints(float x, float y) {
        Node n = new OutputNode(new Coord(x, y));
        NodesToLoad.add(n);
    }

    public static void translateZ(int z) {
        transZ += z;

    }

    /**
     * Calculates the transform from screen coordinate
     * system to world coordinate system coordinates
     * for a specific point, given a camera position.
     *
     * @param touch Coord point of screen touch, the
     *              actual position on physical screen (ej: 160, 240)
     * @return position in WCS.
     */
    public Coord GetWorldCoords(GL10 gl, Coord touch) {

        GL11 gl11 = (GL11) gl;
        GL11ExtensionPack gl11ext = (GL11ExtensionPack) gl;
        int[] viewport = new int[4];
        float[] modelview = new float[16];
        float[] projection = new float[16];
        float winX, winY;
        FloatBuffer winZ = FloatBuffer.allocate(4);

        gl11.glGetFloatv(gl11.GL_MODELVIEW_MATRIX, modelview, 0);       // Retrieve The Modelview Matrix
        gl11.glGetFloatv(gl11.GL_PROJECTION_MATRIX, projection, 0);
        gl11.glGetIntegerv(gl11.GL_VIEWPORT, viewport, 0);

        winX = touch.X;
        winY = (float) viewport[3] - touch.Y;
        gl11.glReadPixels((int) touch.X, (int) winY, 1, 1, gl11ext.GL_DEPTH_COMPONENT, gl11.GL_FLOAT, winZ);


        float[] output = new float[4];
        GLU.gluUnProject(winX, winY, winZ.get(), modelview, 0, projection, 0, viewport, 0, output, 0);
        //Log.w("WorldCoord", output[0] + " , " + output[1]);
        return new Coord(output[0] * Math.abs(transZ), output[1] * Math.abs(transZ));


    }

    public Node objectTouched(Coord glCoord) {
        float x = glCoord.X;
        float y = glCoord.Y;
        for (int j = NodeList.size() - 1; j >= 0; j--) {
            Node c = NodeList.get(j);
            if (x > c.LBound && x < c.RBound && y < c.UBound && y > c.DBound) {
                Log.w("Button dims", "Coord: (" + c.getCoord().X + " , " + c.getCoord().Y + ")" + "     width: " + c.Width + "    height: " + c.Height);
                Log.w("Button TOUCHED", "Coord: (" + x + " , " + y + ")" + "At index:" + j);
                break;
            }
        }

        return null;
    }

    public void setContext(Context context) {
        myContext = context;
    }
}