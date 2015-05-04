package com.example.zmotsing.myapplication.Nodes;

import android.util.Log;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.MyGLRenderer;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by pread on 5/4/15.
 */
public class MathNode extends Node {

    int currentNodeIndex;

    public MathNode(Coord c,CopyOnWriteArrayList<Node> nlist, CopyOnWriteArrayList<Coord> points) {
        super(c,nlist,points);
        drawableInt = R.drawable.mathnode;
        currentNodeIndex = 0;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action(MyGLSurfaceView SV)
    {
        Log.w("NODEACTION", "MATH");
       // MyGLRenderer.Tn.stop();
       // SV.getOutput(this.getID());
    }

    @Override
    protected void setSprite() {
        spr = new Sprite(drawableInt, co.X, co.Y, Width, Height);
        setBounds();
    }



    @Override
    public int getTextureIndex() {
        return 0;
    }


    @Override
    public String getTitle() {
        return "math";
    }
}