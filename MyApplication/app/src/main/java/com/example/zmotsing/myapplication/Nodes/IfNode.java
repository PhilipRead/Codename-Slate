package com.example.zmotsing.myapplication.Nodes;

import android.util.Log;

import com.example.zmotsing.myapplication.CatmullRomType;
import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.LineStrip;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.NodeType;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Spline;
import com.example.zmotsing.myapplication.Sprite;
import com.example.zmotsing.myapplication.MyGLRenderer;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.opengles.GL10;

import static com.example.zmotsing.myapplication.MyGLRenderer.Tn;
import static com.example.zmotsing.myapplication.MyGLRenderer.addControlPoints;

/**
 * Created by acowdrey on 4/27/15.
 */
public class IfNode extends Node {

    public CopyOnWriteArrayList<Coord> ifControlPoints = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Node> ifNodes = new CopyOnWriteArrayList<>();
    LineStrip truepath;
    public IfNode(Coord c,CopyOnWriteArrayList<Node> nlist, CopyOnWriteArrayList<Coord> points) {
        super(c,nlist,points);
        drawableInt = R.drawable.ifnode;
        scalingFactor = .2f;
        AddToLine = 1;
        //ifNodes.add(this);
        MyGLRenderer.CurrNodeList = ifNodes;
        MyGLRenderer.CurrControlPoints = ifControlPoints;
        //ifControlPoints.add(c);
//        MyGLRenderer.MasterNodeList.add(c);
//        MyGLRenderer.MasterControlPoints.add(c);
    }

    @Override
    public void action(MyGLSurfaceView SV) {
        if(Tn.ArrayIndex >50) {
            boolean ifResult = SV.getIfValue(this.getID());
            if (ifResult) {
                Tn.tLineStrip = truepath;
                Tn.tNodeList = ifNodes;
                Tn.ArrayIndex = 0;
            }
        }
    }

    public void translateNodes(float x, float y)
    {
        int i = 1;
        Iterator<Node> irr = ifNodes.iterator();
        irr.next();
        while(irr.hasNext()) {
            Node element = irr.next();
            if(element instanceof IfNode)
            {
                ((IfNode)element).translateNodes(x,y);
            }
            Coord co = element.getCoord();
            co.X += x;
            co.Y += y;
            element.setCoord(co);
            i++;
        }
    }

    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    protected void setSprite() {
        spr = new Sprite(drawableInt, co.X, co.Y, Width, Height);
        setBounds();
    }

    public void drawTruePath(GL10 gl,boolean redrawLines)
    {
        if (redrawLines)
        {
            if(ifControlPoints.size() > 2)
            {
                truepath = new LineStrip(Spline.interpolate(ifControlPoints, 60, CatmullRomType.Chordal));

            }
            else if(ifControlPoints.size() == 2)
            {
                truepath = new LineStrip(ifControlPoints);
            }
        }

        if (truepath != null) {
            truepath.draw(gl); // ( NEW )

        }
        Iterator<Node> irr = ifNodes.iterator();
        irr.next();
        while(irr.hasNext()) {
            Node temp = irr.next();
            if(temp instanceof IfNode)
            {
                ((IfNode)temp).drawTruePath(gl, redrawLines);
            }
            temp.spr.draw(gl);
        }
    }
    @Override
    public String getTitle() {
        return "if";
    }

    public LineStrip getTruePath()
    {
        return truepath;
    }
}
