package com.example.zmotsing.myapplication.Nodes;

import android.util.Log;

import com.example.zmotsing.myapplication.*;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by DreyCow on 11/25/2014.
 */
public class InputNode extends Node {

    public InputNode(Coord c,CopyOnWriteArrayList<Node> nlist, CopyOnWriteArrayList<Coord> points){
        super(c,nlist,points);
        drawableInt = R.drawable.inputnode;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action(MyGLSurfaceView SV) {

        MyGLRenderer.Tn.stop();
        SV.getInput();
        //surfaceview
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


    @Override
    public String getTitle() {
        return "input";
    }
}
