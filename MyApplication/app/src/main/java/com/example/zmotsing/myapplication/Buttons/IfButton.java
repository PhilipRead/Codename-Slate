package com.example.zmotsing.myapplication.Buttons;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

/**
 * Created by acowdrey on 3/4/15.
 */
public class IfButton extends Node {

    public IfButton(Coord c) {
        super(c);
        drawableInt = R.drawable.ifbutton;
        scalingFactor = .2f;
        AddToLine = 0;
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
        return null;
    }
}
