package com.example.zmotsing.myapplication.Buttons;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.NodeType;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pread on 5/4/15.
 */
public class SetButton extends Node {

    public SetButton(Coord c) {
        super(c,null,null);
        drawableInt = R.drawable.setbutton;
        drawableIntOptional = R.drawable.setbutton_pressed;
        scalingFactor = .2f;
        AddToLine = 0;

    }

    @Override
    public void action(MyGLSurfaceView SV) {
        com.example.zmotsing.myapplication.MyGLRenderer.nodeTypeCreate= NodeType.SET;
    }

    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    protected void setSprite() {
        //Coord
        spr = new Sprite(drawableInt, co.X, co.Y, Width, Height);
        //spr.printcoord = true;
        sprOptional = new Sprite(drawableIntOptional, co.X, co.Y, Width, Height);
        setBounds();
    }

    @Override
    public void draw(GL10 gl) {
        spr.draw(gl);
    }

    @Override
    public void drawPressed(GL10 gl){
        sprOptional.draw(gl);
    }

    @Override
    public String getTitle() {
        return null;
    }
}
