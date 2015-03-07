package com.example.zmotsing.myapplication.Buttons;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.NodeType;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

/**
 * Created by acowdrey on 3/4/15.
 */
public class InputButton extends Node {

    public InputButton(Coord c) {
        super(c);
        drawableInt = R.drawable.inputbutton;
        scalingFactor = .2f;
        AddToLine = 0;

    }

    @Override
    public void action() {
        com.example.zmotsing.myapplication.MyGLRenderer.nodeTypeCreate= NodeType.INPUT;

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
        return null;
    }
}
