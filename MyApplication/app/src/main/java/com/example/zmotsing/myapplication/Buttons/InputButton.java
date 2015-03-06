package com.example.zmotsing.myapplication.Buttons;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.Node;
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

    }

    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    protected void setSprite() {

        spr = new Sprite(drawableInt, co.X, co.Y, 0.8f, 0.2f);
        LBound = co.X - (Width * .5f);
        RBound = co.X + (Width * .5f);
        UBound = co.Y + (Height * .5f);
        DBound = co.Y - (Width * .5f);
    }

    @Override
    public String getTitle() {
        return null;
    }
}
