package com.example.zmotsing.myapplication.Nodes;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

/**
 * Created by DreyCow on 11/25/2014.
 */
public class OutputNode extends Node {

    int currentNodeIndex;

    public OutputNode(Coord c) {
        super(c);
        drawableInt = R.drawable.outputnode;
        currentNodeIndex = 0;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action() {
        //todo
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
