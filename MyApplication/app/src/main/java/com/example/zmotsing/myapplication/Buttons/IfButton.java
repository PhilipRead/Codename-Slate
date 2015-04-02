package com.example.zmotsing.myapplication.Buttons;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by acowdrey on 3/4/15.
 */
public class IfButton extends Node {

    public IfButton(Coord c) {
        super(c);
        drawableInt = R.drawable.ifbutton;
        drawableIntOptional = R.drawable.ifbutton_pressed;
        scalingFactor = .2f;
        AddToLine = 0;
    }

    @Override
    public void action(MyGLSurfaceView SV) {

    }

    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    protected void setSprite() {
        spr = new Sprite(drawableInt, co.X, co.Y, Width, Height);
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
