package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Build;
import android.os.Handler;

import com.example.zmotsing.myapplication.Nodes.OutputNode;
import com.example.zmotsing.myapplication.Nodes.TravelingNode;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by acowdrey on 11/12/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Sprite 		square;		// the square

    private int[] Textures = new int[1]; //textures pointers
    private int  activeTexture = 0; //which texture is active (by index)

    int i=0;
    final Handler myHandler = new Handler();
    private static Context myContext;
    private TravelingNode Tn;

    /** Constructor to set the handed over context */
    public MyGLRenderer() {
        this.square		= new Sprite(R.drawable.android,0,0);

    }


    public static LineStrip linestrip;
    static CopyOnWriteArrayList<Node> NodeList = new CopyOnWriteArrayList<>();
    public static TextManager textMngr = new TextManager(-0.8f, 0.0f, 1.2f, 0.0f);


    static CopyOnWriteArrayList<Coord> controlPoints = new CopyOnWriteArrayList<Coord>();
    public static CopyOnWriteArrayList<Node> NodesToLoad = new CopyOnWriteArrayList<>();



    @Override
    public void onDrawFrame(GL10 gl) {

        Iterator<Node> itrl = NodesToLoad.iterator();
        while(itrl.hasNext()) {
            Node element = itrl.next();
            element.spr.loadGLTexture(gl, myContext);
        }
        NodesToLoad.clear();


        // Clears the screen and depth buffer.

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | //
                GL10.GL_DEPTH_BUFFER_BIT);

        // Draw our square.
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);
//        square.draw(gl);



        Iterator<Node> itr = NodeList.iterator();
        while(itr.hasNext())
        {
            Node element = itr.next();
            element.spr.draw(gl);
        }
        //NodeList.get(0).spr.draw(gl);
        if(linestrip != null) {
            linestrip.draw(gl); // ( NEW )

        }

        Iterator<TextObject> itrText = textMngr.getTextList().iterator();
        while(itrText.hasNext())
        {
            TextObject element = itrText.next();
            element.spr.draw(gl);
        }

        Tn.spr.draw(gl);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(width/3, 0, ((2*width)/3), height);  //right 2/3rds of the screen
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

        NodeList.add(new OutputNode(new Coord(-0.6f,  -0.6f)));
        NodeList.add(new OutputNode(new Coord(0.6f,  -0.6f)));
        NodeList.add(new OutputNode(new Coord(0.6f,  0.6f)));
        NodeList.add(new OutputNode(new Coord(0.6f, 1f)));
        NodeList.add(new OutputNode(new Coord(0.2f,  -1f)));
        Tn = new TravelingNode(new Coord(-0.6f,  -0.6f));
        textMngr.addText("ABORS");

        //nody = new NodeSprite();
        for(Node c : NodeList)
        {
            c.spr.loadGLTexture(gl, myContext);
        }
        for(TextObject c : textMngr.getTextList())
        {
            c.spr.loadGLTexture(gl, myContext);
        }

        Tn.spr.loadGLTexture(gl, myContext);
        //square.loadGLTexture(gl, myContext);

        gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
        gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
        gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do

        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        //this.lastProjectionMat = new float[16];
        //this.lastModelViewMat = new float[16];

        for(Node c : NodeList)
        {
            controlPoints.add(c.getCoord());
        }

        linestrip = new LineStrip(Spline.interpolate(controlPoints,60,CatmullRomType.Chordal));


        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {AdvanceTravelingNode();}
        }, 0, 10);
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

    public static void addControlPoints(float x, float y)
    {

        Node n = new OutputNode(new Coord(x,y));
        NodeList.add(n);
        controlPoints.add(new Coord(x,y));
        NodesToLoad.add(n);
        //NodeList.get(NodeList.size()).spr.loadGLTexture(gl, myContext);
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

        screenW = (screenW*2)/3;

        float oglTouchY = screenH - touch.Y;
        //float oglTouchX = touch.X - screenW * .333f;

       /* Transform the screen point to clip space in ogl (-1,1) */
        float glWidth = (float) ((touch.X) * 6f / screenW - 6);
        float glHeight = (float) ((oglTouchY) * 3.2f / screenH -1.5);

        worldPos = new Coord(glWidth, glHeight);

        return worldPos;
    }


    public void setContext(Context context) {
        myContext = context;
    }
}