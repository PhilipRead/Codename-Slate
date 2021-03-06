package com.example.zmotsing.myapplication.Nodes;

import android.util.Log;

import com.example.zmotsing.myapplication.*;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by DreyCow on 11/25/2014.
 */
public class OutputNode extends Node {

    int currentNodeIndex;

    public OutputNode(Coord c,CopyOnWriteArrayList<Node> nlist, CopyOnWriteArrayList<Coord> points) {
        super(c,nlist,points);
        drawableInt = R.drawable.outputnode;
        currentNodeIndex = 0;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action(MyGLSurfaceView SV)
    {
        Log.w("NODEACTION", "OUTPUT");
        MyGLRenderer.Tn.stop();
        SV.getOutput(this.getID());
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
        return "output";
    }
}
