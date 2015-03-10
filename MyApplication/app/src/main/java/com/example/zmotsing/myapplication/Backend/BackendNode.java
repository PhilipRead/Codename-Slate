package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 12/11/14.
 */

public class BackendNode
{
    private int id;

    BackendNode(String i)
    {
        id = Integer.parseInt(i);
    }

    int getId()
    {
        return id;
    }
}
