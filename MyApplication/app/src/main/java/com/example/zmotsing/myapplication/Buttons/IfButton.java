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
        spr = new Sprite(R.drawable.ifbutton,c.X,c.Y,0.8f,0.2f);
    }

    @Override
    public void action() {

    }

    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
