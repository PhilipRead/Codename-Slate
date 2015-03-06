package com.example.zmotsing.myapplication.Nodes;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.Node;

/**
 * Created by DreyCow on 11/25/2014.
 */
public class InputNode extends Node {

    public InputNode(Coord c) {

        super(c);
    }

    @Override
    public void action() {
        //todo
    }


    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    protected void setSprite() {

    }


    @Override
    public String getTitle() {
        return "input";
    }
}
