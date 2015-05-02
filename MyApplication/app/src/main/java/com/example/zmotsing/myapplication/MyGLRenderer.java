package com.example.zmotsing.myapplication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Build;
import android.os.Handler;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.zmotsing.myapplication.Backend.BackendLogic;
import com.example.zmotsing.myapplication.Buttons.*;
import com.example.zmotsing.myapplication.Nodes.*;
import java.nio.FloatBuffer;
import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

import static java.security.AccessController.getContext;


/**
 * Created by acowdrey on 11/12/14.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyGLRenderer implements GLSurfaceView.Renderer {

    //region Renderer Variables
    final Handler TnHandler = new Handler();

    private int[] Textures = new int[1]; //textures pointers
    private int activeTexture = 0; //which texture is active (by index)

    int i = 0;
    private static Context myContext;
    public static TravelingNode Tn;
    public static BackgroundNode Bn;

    public static boolean action_flag = false;
    public static NodeType nodeTypeCreate = null;
    static float transX;
    static float transY;
    static float transZ = -4;
    public MyGLSurfaceView surfaceview;
    public MyGLRenderer() {}
    public static LineStrip currentLineStrip;
    public static LineStrip startLineStrip;
    public static Coord TouchEventCoord;
    public static Coord TouchDownCoord;
    public static boolean TouchedDown;
    public static boolean Touched;
    public static boolean bindMode;
    public static boolean RedrawLine;
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
    public static boolean nodeMoved;
    public static boolean pointerDown;
    public static boolean pinchMoved;
    public static Node curPressed;
    public static Node nodeWaitingBind;
    float viewwidth;
    float viewheight;
    public static CopyOnWriteArrayList<Node> MasterNodeList = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Node> StartNodeList = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Node> CurrNodeList = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Node> ifTempList = null;
    static CopyOnWriteArrayList<Node> ButtonList = new CopyOnWriteArrayList<>();
    public static TextManager inputTxt = new TextManager(1.0f, 0.0f, 0.5f, 0.0f);
    public static TextManager outputTxt = new TextManager(1.0f, 0.0f, 0.0f, 0.0f);


    static CopyOnWriteArrayList<Coord> startControlPoints = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Coord> MasterControlPoints = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Coord> currControlPoints = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Coord> ifTempPoints = null;
    public static CopyOnWriteArrayList<Node> ButtonsToLoad = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Node> NodesToLoad = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<String> inputTxtToLoad = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<String> outputTxtToLoad = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Node> bindableNodes = new CopyOnWriteArrayList<>();
    //endregion

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
        if(n.sprOptional != null) {
            n.sprOptional.loadGLTexture(gl, myContext);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        //region Load in all graphics for new nodes
        for (Node element : NodesToLoad) {
            setupGraphic(gl,element,false);
            if(ifTempList != null  && element instanceof IfNode)
            {
                boolean ifHasEndNode = (ifTempList.size() > 1);

                if (true)
                {

                    ifTempList.add(ifTempList.size()-1,element);
                    ifTempPoints.add(ifTempPoints.size()-1,element.getCoord());
                    CurrNodeList.add(CurrNodeList.size() - 1, element);
                    currControlPoints.add(currControlPoints.size() - 1, element.getCoord());
                }
                else
                {

                    ifTempList.add(element);
                    ifTempPoints.add(element.getCoord());
                    CurrNodeList.add(element);
                    currControlPoints.add(element.getCoord());
                }
                ifTempList = null;
            }
            else
            {
                boolean currhasEndNode = (CurrNodeList.size() > 1);

                if (currhasEndNode)
                {
                    CurrNodeList.add(CurrNodeList.size() - 1, element);
                    currControlPoints.add(currControlPoints.size() - 1, element.getCoord());
                }
                else
                {
                    CurrNodeList.add(element);
                    currControlPoints.add(element.getCoord());
                }
            }
            MasterNodeList.add(element);
            MasterControlPoints.add(element.getCoord());
            RedrawLine = true;

        }

        NodesToLoad.clear();

        for (Node element : ButtonsToLoad) {

            //Node n = new OutputButton(element.co);
            setupGraphic(gl,element,true);
            ButtonList.add(element);
        }

        ButtonsToLoad.clear();

        for(String element : inputTxtToLoad){
            TextObject [] tempArr = inputTxt.addText(element);
            for(int i=0; i<tempArr.length; i++)
            {
                tempArr[i].spr.loadGLTexture(gl, myContext);
            }

        }

        inputTxtToLoad.clear();

        for(String element : outputTxtToLoad){
            TextObject [] tempArr = outputTxt.addText(element);
            for(int i=0; i<tempArr.length; i++)
            {
                tempArr[i].spr.loadGLTexture(gl, myContext);
            }

        }

        outputTxtToLoad.clear();
        //endregion

        //region Redraw linestrip
        boolean redrawAllLines = false;
        if (RedrawLine)
        {
            redrawAllLines = true;
            if(startControlPoints.size() > 2)
            {
                startLineStrip = new LineStrip(Spline.interpolate(startControlPoints, 60, CatmullRomType.Chordal));

            }
            else if(startControlPoints.size() == 2)
            {
                startLineStrip = new LineStrip(startControlPoints);
            }
            RedrawLine = false;
        }
        //endregion

        //region Touch Motion Detection Logic
        if(actionDown){
            actionDown = false;
            actionDownCoordGL = GetWorldCoords(gl, actionDownCoord);

            if(getNodeTouched(actionDownCoordGL,MasterNodeList, false) != null) {
                MyGLSurfaceView.moveNodeTimer = new Timer();
                MyGLSurfaceView.moveNodeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        MyGLSurfaceView.swipeMode = false;
                        MyGLSurfaceView.nodeMoveMode = true;
                    }
                }, 1000);
            }
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
        }else if(nodeMoved){
            nodeMoved = false;
            actionMovedCoordGL = GetWorldCoords(gl, actionMovedCoord);
            Node tempNode = getNodeTouched(actionMovedCoordGL, MasterNodeList, false);

            if(tempNode != null) {
                int tempint = MasterNodeList.indexOf(tempNode);
                Coord tempc = MasterControlPoints.get(tempint);
                tempc.X =actionMovedCoordGL.X;
                tempc.Y =actionMovedCoordGL.Y;
                tempNode.setCoord(tempc);
                RedrawLine = true;
            }


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
        //endregion

        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        //region Translation for swipe
        if(transY != 0 && transX != 0)
        {
            float tempX = transX;
            float tempY = transY;
            transX = transY = 0;
            int i = 0;
            for (Node element : StartNodeList) {

                Coord co = element.getCoord();
                co.X += tempX;
                co.Y += tempY;

                if(element instanceof IfNode)
                {
                    ((IfNode)element).translateNodes(tempX,tempY);
                    ((IfNode)element).ifControlPoints.set(0,co);
                }

                startControlPoints.set(i, co);
                element.setCoord(co);
                i++;
            }
            RedrawLine = true;
        }
        //endregion

        //region Draw All Elements

        switchToOrtho(gl);
        Bn.spr.draw(gl);
        switchBackToFrustum(gl);
        gl.glTranslatef(0, 0, transZ);

        for (TextObject element : inputTxt.getTextList()) {
            element.spr.draw(gl);
        }

        for(TextObject element: outputTxt.getTextList()) {
            element.spr.draw(gl);
        }

        if (startLineStrip != null) {
            startLineStrip.draw(gl); // ( NEW )

        }
        for (Node element : StartNodeList) {
            if(element instanceof IfNode)
            {
                ((IfNode)element).drawTruePath(gl, redrawAllLines);
            }
            element.spr.draw(gl);
        }
        redrawAllLines = false;
        Tn.spr.draw(gl);

        switchToOrtho(gl);

        for (Node element : ButtonList) {
            if(element == curPressed)
            {
                element.drawPressed(gl);
            }
            else
            {
                element.draw(gl);
            }
        }
        //endregion

        //region ButtonDown detection
        if (TouchedDown) {
            TouchedDown = false;
            Node n = getNodeTouched(GetWorldCoords(gl,TouchDownCoord), ButtonList, true);

            curPressed = n;

        }
        //endregion

        //region NodeBinding and ButtonPress Detection
        if (Touched) {
            Touched = false;

            if(bindMode)
            {
                switchBackToFrustum(gl);
                Node bindNode = getNodeTouched(GetWorldCoords(gl, TouchEventCoord), bindableNodes, false);
                switchToOrtho(gl);
                if(bindNode != null)
                {
                    bindMode = false;
                    BackendLogic.initializeOutputNode(nodeWaitingBind.getID(), bindNode.getID());
                }

            }
            else
            {
                Node n = getNodeTouched(GetWorldCoords(gl,TouchDownCoord), ButtonList, true);
                if (n != null) {
                    n.action(surfaceview);
                }
            }

        }
        //endregion

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

        RedrawLine = false;
        currControlPoints = startControlPoints;
        CurrNodeList = StartNodeList;
        nodeTypeCreate = NodeType.START;
        addControlPoints(200f, 200f);
        nodeTypeCreate = NodeType.END;
        addControlPoints(400f, 200f);
//        nodeTypeCreate = NodeType.OUTPUT;
//        addControlPoints(200f, 100f);
//        nodeTypeCreate = NodeType.OUTPUT;
//        addControlPoints(250f, 50f);
//        nodeTypeCreate = NodeType.OUTPUT;
//        addControlPoints(400f, 26f);

        Tn = new TravelingNode(new Coord(-0.6f, -0.6f));
        Bn = new BackgroundNode(new Coord(0f, 0f));

        final Runnable myRunnable = new Runnable() {
            public void run() {
                Tn.action(surfaceview);
            }
        };

        Tn.setHandler(myRunnable,TnHandler);
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
        ButtonsToLoad.add(new PlayButton(new Coord(0.88f, 0.81f)));

        //nody = new NodeSprite();
        for (TextObject c : inputTxt.getTextList()) {
            c.spr.loadGLTexture(gl, myContext);
        }

        for (TextObject c : outputTxt.getTextList()) {
            c.spr.loadGLTexture(gl, myContext);
        }


        Bn.setSprite();
        Bn.spr.loadGLTexture(gl,myContext);

        Tn.setSprite();
        Tn.spr.loadGLTexture(gl, myContext);
        //Tn.start();
        //square.loadGLTexture(gl, myContext);


        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping
        gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    //Black Background
        gl.glClearDepthf(1.0f);                    //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);            //The Type Of Depth Testing To Do

        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


        for (Node c : StartNodeList) {
            startControlPoints.add(c.getCoord());
        }

        startLineStrip = new LineStrip(Spline.interpolate(startControlPoints, 60, CatmullRomType.Chordal));
    }


    private static String tempBuffer;

    public static void addControlPoints(float x, float y) {


        Node n = null;
        if(nodeTypeCreate != null)
        {
            switch  (nodeTypeCreate)
            {
                case INPUT:

                    n = new InputNode(new Coord(x, y));

                    BackendLogic.initializeInputNode(n.getID());

                    bindableNodes.add(n);
                    break;
                case OUTPUT:
                    //region OutputNode Fold
                    n = new OutputNode(new Coord(x, y));

                    final Node curNode = n;

                    AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                    builder.setPositiveButton("Node Value", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            nodeWaitingBind = curNode;
                            bindMode = true;
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Set Constant", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final int nID = curNode.getID();
                            tempBuffer = "";
                            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                            final TextView textView = new TextView(myContext);

                            textView.setHeight(50);

                            textView.setTextColor(Color.GREEN);
                            textView.setBackgroundColor(Color.BLACK);
                            builder.setView(textView)
                                    .setCancelable(false)
                                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                                        @Override
                                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                            if (event.getAction() == KeyEvent.ACTION_UP) {
                                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                                    BackendLogic.initializeOutputNode(nID, tempBuffer);
                                                    ((InputMethodManager) myContext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                                                    dialog.dismiss();

                                                    return true;
                                                } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                                                    int bufLength = tempBuffer.length();

                                                    if (bufLength > 0) {
                                                        tempBuffer = tempBuffer.substring(0, bufLength - 1);

                                                        CharSequence tempTxt = textView.getText();
                                                        CharSequence newTxt = tempTxt.subSequence(0, tempTxt.length() - 1);
                                                        textView.setText(newTxt);
                                                    }

                                                    return true;
                                                }

                                                char tempChar = (char) event.getUnicodeChar();
                                                textView.append(tempChar + "");
                                                tempBuffer += tempChar;
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
                                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                            ((InputMethodManager) myContext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                            dialog.cancel();
                        }

                    });


                    AlertDialog alert = builder.create();
                    alert.show();

                    break;
                //endregion
                case START:
                    n = new StartNode(new Coord(x, y));
                    break;
                case END:
                    n = new EndNode(new Coord(x, y));
                    break;
                case IF:
                    ifTempList = CurrNodeList;
                    ifTempPoints = currControlPoints;
                    n = new IfNode(new Coord(x, y));
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

    public void setContext(Context context) {
        myContext = context;
    }

    public Node getNodeTouched(Coord glCoord, CopyOnWriteArrayList<Node> nList, boolean isOrtho)
    {
        int offset = isOrtho? 4:1;
        float x = glCoord.X/offset;
        float y = glCoord.Y/offset;
        for (int j = nList.size() - 1; j >= 0; j--) {
            Node c = nList.get(j);
            if (x > c.LBound && x < c.RBound && y < c.UBound && y > c.DBound) {
                Log.w("Node dims", "Coord: (" + c.getCoord().X + " , " + c.getCoord().Y + ")" + "     width: " + c.Width + "    height: " + c.Height);
                Log.w("Node TOUCHED", "Coord: (" + x + " , " + y + ")" + "At index:" + j);
                return c;
            }
        }

        return null;
    }


}