package com.example.zmotsing.myapplication;

/**
 * Created by DreyCow on 11/25/2014.
 */
public abstract class Node {

    protected Coord co;
    protected float Width;
    protected float Height;
    protected int drawableInt;
    protected float scalingFactor;
    protected int AddToLine;
    protected float LBound, RBound, UBound, DBound;

    public Node(Coord c) {
        co = c;

    }


    public Coord getCoord() {
        return co;
    }

    public void setCoord(Coord c) {
        co = c;
    }

    abstract public void action();

    public Sprite spr;

    abstract public int getTextureIndex();

    protected abstract void setSprite();

    abstract public String getTitle();
}
