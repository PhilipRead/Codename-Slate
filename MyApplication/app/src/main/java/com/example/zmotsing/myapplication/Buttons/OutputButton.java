package com.example.zmotsing.myapplication.Buttons;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.NodeType;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by acowdrey on 3/4/15.
 */
public class OutputButton extends Node {

    public OutputButton(Coord c) {
        super(c);
        drawableInt = R.drawable.outputbutton;
        drawableIntOptional = R.drawable.outputbutton_pressed;
        scalingFactor = .2f;
        AddToLine = 0;
        pressed = false;

    }

    @Override
    public void action() {
        com.example.zmotsing.myapplication.MyGLRenderer.nodeTypeCreate= NodeType.OUTPUT;
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
        if(pressed){
            sprOptional.draw(gl);
        }
        else {
            spr.draw(gl);
        }
    }

    @Override
    public String getTitle() {
        return null;
    }
}
