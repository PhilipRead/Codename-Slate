package com.example.zmotsing.myapplication;

/**
 * Created by DreyCow on 11/28/2014.
 */
public class Coord {
    public float X;
    public float Y;
    public float Z;

    public Coord(float x, float y) {
        X = x;
        Y = y;
        Z = 0;
    }

    public Coord(float x, float y, float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Coord copy() {
        return new Coord(X, Y, Z);
    }

    public boolean intersects2D(Coord coord) {

        return ((coord.X == this.X) && (coord.Y == this.Y));
    }
}
