package com.example.zmotsing.myapplication;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by DreyCow on 11/25/2014.
 */

public abstract class Node {

    protected Coord co;
    protected float Z;
    protected float Width;
    protected float Height;
    protected int drawableInt;
    protected int drawableIntOptional;
    protected float scalingFactor;
    protected int AddToLine;
    protected float LBound, RBound, UBound, DBound;

    protected static int nextID = 0;
    private int id;

    public Node(Coord c) {
        co = c;
        Z = 0f;
        id = nextID;
        ++nextID;

    }

    public  void setBounds()
    {
        LBound = co.X - Width;
        RBound = co.X + Width;
        UBound = co.Y + Height;
        DBound = co.Y - Height;
    }

    public Coord getCoord() {
        return co;
    }

    public void setCoord(Coord c) {
        co = c;
    }

    abstract public void action(MyGLSurfaceView SV);

    public Sprite spr;
    public Sprite sprOptional = null;

    abstract public int getTextureIndex();

    protected abstract void setSprite();

    abstract public String getTitle();

    public  void draw(GL10 gl){};

    public void drawPressed(GL10 gl) {};

    public int getID() { return id; }
}
