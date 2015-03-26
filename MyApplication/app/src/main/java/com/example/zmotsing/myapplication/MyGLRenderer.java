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
import com.example.zmotsing.myapplication.Nodes.InputNode;
import com.example.zmotsing.myapplication.Nodes.OutputNode;
import com.example.zmotsing.myapplication.Nodes.TravelingNode;

import java.nio.FloatBuffer;
import java.util.Timer;
import java.util.TimerTask;
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

    public static NodeType nodeTypeCreate = null;
    static float transX;
    static float transY;
    static float transZ = -4;
    /**
     * Constructor to set the handed over context
     */
    public MyGLRenderer() {


    }


    public static LineStrip linestrip;
    public static Coord TouchEventCoord;
    public static boolean Touched;
    public static Coord actionDownCoord;
    public static Coord actionDownCoordGL;
    public static Coord pointerDownCoord;
    public static Coord pointerDownCoordGL;
    public static Coord pointerMovedCoord;
    public static Coord pointerMovedCoordGL;
    public static Coord actionMovedCoord;
    public static Coord actionMovedCoordGL;
    public static double spacing;
    public static boolean actionDown;
    public static boolean actionMoved;
    public static boolean pointerDown;
    public static boolean pinchMoved;
    public static Node n;
    float viewwidth;
    float viewheight;
    
    static CopyOnWriteArrayList<Node> NodeList = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Node> ButtonList = new CopyOnWriteArrayList<>();
    public static TextManager inputTxt = new TextManager(1.0f, 0.0f, 0.5f, 0.0f);
    public static TextManager outputTxt = new TextManager(1.0f, 0.0f, 0.0f, 0.0f);


    static CopyOnWriteArrayList<Coord> controlPoints = new CopyOnWriteArrayList<Coord>();
    public static CopyOnWriteArrayList<Node> ButtonsToLoad = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Node> NodesToLoad = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<TextObject> inputTxtToLoad = new CopyOnWriteArrayList<>();


    private void setupGraphic(GL10 gl, Node n, boolean isOrtho)
    {
        //get the screen coordinate
        Coord ScreenCoord = n.getCoord();

        //get the drawable for this node
        Drawable d = myContext.getResources().getDrawable(n.drawableInt);

        Coord ScreenBRCoord;
        Coord NewScreenCoord;


        if(isOrtho)
        {
            n.Width = d.getIntrinsicWidth()/1200f;
            n.Height = d.getIntrinsicHeight()/700f;
            n.setCoord(ScreenCoord);
        }
        else {
            //get the bottom right hand coordinate, scaling accordingly using scaling factor (scaling factor of one is actual size)
            ScreenBRCoord = GetWorldCoords(gl, new Coord(ScreenCoord.X + n.scalingFactor * d.getIntrinsicWidth(), ScreenCoord.Y + n.scalingFactor * d.getIntrinsicHeight()));

            //put the screen coordinates into opengl coordinates
            NewScreenCoord = GetWorldCoords(gl, ScreenCoord);

            //fin the opengl width and height
            n.setCoord(NewScreenCoord);
            n.Height = NewScreenCoord.Y - ScreenBRCoord.Y;
            n.Width = ScreenBRCoord.X - NewScreenCoord.X;

        }


        n.setSprite();
        n.spr.loadGLTexture(gl, myContext);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        //Load in all graphics for new nodes
        boolean RedrawLine = false;
        for (Node element : NodesToLoad) {

            setupGraphic(gl,element,false);
            NodeList.add(element);
            controlPoints.add(element.getCoord());
            RedrawLine = true;

        }
        NodesToLoad.clear();

        for (Node element : ButtonsToLoad) {

            //Node n = new OutputButton(element.co);
            setupGraphic(gl,element,true);
            ButtonList.add(element);
        }

        ButtonsToLoad.clear();

        for(TextObject element : inputTxtToLoad){
            inputTxt.getTextList().add(element);
        }

        inputTxtToLoad.clear();

        if (RedrawLine && controlPoints.size() > 2) {
            linestrip = new LineStrip(Spline.interpolate(controlPoints, 60, CatmullRomType.Chordal));
            //objectTouched();
        }

        if(actionDown){
            actionDown = false;
            actionDownCoordGL = GetWorldCoords(gl, actionDownCoord);
        }

        if(pointerDown){
            pointerDown = false;
            pointerDownCoordGL = GetWorldCoords(gl, pointerDownCoord);
            spacing = Math.sqrt(
                    Math.pow((actionDownCoordGL.X - pointerDownCoordGL.X), 2) +
                    Math.pow((actionDownCoordGL.Y - pointerDownCoordGL.Y), 2));
        }

        if(actionMoved){
            actionMoved = false;
            actionMovedCoordGL = GetWorldCoords(gl, actionMovedCoord);

            translateX(actionMovedCoordGL.X - actionDownCoordGL.X);
            translateY(actionMovedCoordGL.Y - actionDownCoordGL.Y);

            actionDownCoordGL = actionMovedCoordGL;
        }else if(pinchMoved){
            pinchMoved = false;
            actionMovedCoordGL = GetWorldCoords(gl, actionMovedCoord);
            pointerMovedCoordGL = GetWorldCoords(gl, pointerMovedCoord);

            double newSpacing = Math.sqrt(
                    Math.pow((actionMovedCoordGL.X - pointerMovedCoordGL.X), 2) +
                    Math.pow((actionMovedCoordGL.Y - pointerMovedCoordGL.Y), 2));

            transZ += (newSpacing - spacing)*1.4;



            spacing =  newSpacing;



        }


        // Clears the screen and depth buffer.

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | //
                GL10.GL_DEPTH_BUFFER_BIT);

        // Draw our square.
        // Translates 4 units into the screen.
//        square.draw(gl);

        gl.glTranslatef(transX, transY, transZ);


        for (TextObject element : inputTxt.getTextList()) {
            element.spr.draw(gl);
        }

        for(TextObject element: outputTxt.getTextList()) {
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

        switchToOrtho(gl);


        if (Touched) {
            Touched = false;
            Node n = objectTouched(GetWorldCoords(gl,TouchEventCoord));
            if(n!=null){n.action();}

        }

        for (Node element : ButtonList) {
            element.draw(gl);
        }
        switchBackToFrustum(gl);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
    }
    public void switchBackToFrustum(GL10 gl)
    {
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(gl.GL_MODELVIEW);
    }
    public void switchToOrtho(GL10 gl)
    {
        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        //gl.glViewport(0, 0, viewwidth, viewheight);
        //GLU.gluOrtho2D(gl,0f, viewwidth,0f, viewheight);
        //GLU.gluOrtho2D(gl,0f, viewwidth,viewheight,0f );
        //gl.glOrthof(0, 558, 0, 321, -5, 1);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        //sets the viewport size
        gl.glViewport(0, 0, width, height);  //WHOLE SCREEN
        viewwidth = width;
        viewheight = height;
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

        nodeTypeCreate = NodeType.OUTPUT;
        addControlPoints(200f, 200f);
        nodeTypeCreate = NodeType.OUTPUT;
        addControlPoints(100f, 200f);
        nodeTypeCreate = NodeType.OUTPUT;
        addControlPoints(200f, 100f);
        nodeTypeCreate = NodeType.OUTPUT;
        addControlPoints(250f, 50f);
        nodeTypeCreate = NodeType.OUTPUT;
        addControlPoints(400f, 26f);

        Tn = new TravelingNode(new Coord(-0.6f, -0.6f));
        float bs = 1.61f; // buttonspacing

//        ButtonList.add(new OutputButton(new Coord(-2.1f, 1.5f)));
//        ButtonList.add(new InputButton(new Coord(-.49f, 1.5f)));
//        ButtonList.add(new IfButton(new Coord(1.12f, 1.5f)));
        float button_x = -0.78f;
        float button_y = 0.9f;
        float button_dx = .427f;
        float button_dy = 0f;

        ButtonsToLoad.add(new OutputButton(new Coord(button_x, button_y)));
        button_x += button_dx;button_y += button_dy;
        ButtonsToLoad.add(new InputButton(new Coord(button_x, button_y)));
        button_x += button_dx;button_y += button_dy;
        ButtonsToLoad.add(new IfButton(new Coord(button_x, button_y)));
        button_x += button_dx;button_y += button_dy;

        inputTxt.addText("INPUT");
        outputTxt.addText("OUTPUT");

        //nody = new NodeSprite();
        for (TextObject c : inputTxt.getTextList()) {
            c.spr.loadGLTexture(gl, myContext);
        }

        for (TextObject c : outputTxt.getTextList()) {
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

    public static void addControlPoints(float x, float y) {

        Node n = null;
        if(nodeTypeCreate != null)
        {
            switch  (nodeTypeCreate)
            {
                case INPUT:

                    n = new InputNode(new Coord(x, y));
                    break;
                case OUTPUT:
                    n = new OutputNode(new Coord(x, y));
                    break;
                case IF:
                   // n = new OutputNode(new Coord(x, y));
                    break;
            }
            nodeTypeCreate = null;
        }
        else
        {
            return;
        }
        NodesToLoad.add(n);
    }

    public static void translateZ(int z) {
        transZ += z;

    }
    public static void translateY(float y) {
        transY += y;

    }
    public static void translateX(float x) {
        transX += x;

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
    public Coord GetWorldCoords(GL10 gl, Coord touch, float z) {

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


    public Coord GetWorldCoords(GL10 gl, Coord touch) {

        return GetWorldCoords(gl,touch, 0);

    }

    public Node objectTouched(Coord glCoord) {
        float x = glCoord.X/4;
        float y = glCoord.Y/4;
        for (int j = ButtonList.size() - 1; j >= 0; j--) {
            Node c = ButtonList.get(j);
            if (x > c.LBound && x < c.RBound && y < c.UBound && y > c.DBound) {
                Log.w("Button dims", "Coord: (" + c.getCoord().X + " , " + c.getCoord().Y + ")" + "     width: " + c.Width + "    height: " + c.Height);
                Log.w("Button TOUCHED", "Coord: (" + x + " , " + y + ")" + "At index:" + j);
                return c;
            }
        }

        return null;
    }

    public void setContext(Context context) {
        myContext = context;
    }

}