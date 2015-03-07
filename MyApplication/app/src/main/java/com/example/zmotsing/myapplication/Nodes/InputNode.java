package com.example.zmotsing.myapplication.Nodes;

import com.example.zmotsing.myapplication.*;

/**
 * Created by DreyCow on 11/25/2014.
 */
public class InputNode extends Node {

    public InputNode(Coord c) {
        super(c);
        drawableInt = R.drawable.inputnode;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action() {
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
