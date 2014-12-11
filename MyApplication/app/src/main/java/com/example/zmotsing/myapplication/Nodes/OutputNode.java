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
        spr = new Sprite(R.drawable.outputnode,c.X,c.Y);
        currentNodeIndex = 0;
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
    public String getTitle() {
        return "output";
    }
}
