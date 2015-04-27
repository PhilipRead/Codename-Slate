package com.example.zmotsing.myapplication.Nodes;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.LineStrip;
import com.example.zmotsing.myapplication.MyGLRenderer;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

/**
 * Created by acowdrey on 4/27/15.
 */
public class IfNode extends Node {

    LineStrip truepath;
    public IfNode(Coord c) {
        super(c);
        drawableInt = R.drawable.ifnode;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action(MyGLSurfaceView SV) {

       //MyGLRenderer.Tn.stop();
       //SV.getInput();
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
        return "if";
    }

    public LineStrip getTruePath()
    {
        return truepath;
    }
}
