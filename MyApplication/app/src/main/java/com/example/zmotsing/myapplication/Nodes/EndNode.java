package com.example.zmotsing.myapplication.Nodes;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.LineStrip;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by acowdrey on 4/27/15.
 */
public class EndNode extends Node {

    int currentNodeIndex;

    public EndNode(Coord c, CopyOnWriteArrayList<Node> nlist, CopyOnWriteArrayList<Coord> points) {
        super(c,nlist,points);
        drawableInt = R.drawable.endnode;
        currentNodeIndex = 0;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action(MyGLSurfaceView SV)
    {
//        Log.w("NODEACTION", "OUTPUT");
//        MyGLRenderer.Tn.stop();
//        SV.getOutput();
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
        return "end";
    }
}
