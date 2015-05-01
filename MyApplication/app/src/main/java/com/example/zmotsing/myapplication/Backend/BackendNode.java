package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 12/11/14.
 */

public class BackendNode
{
    private int id;
    private String value;
    private boolean bindable;

    BackendNode(int i)
    {
        id = i;
        value = null;
    }

    BackendNode(int i, String newValue)
    {
        id = i;
        value = newValue;
    }

    int getId()
    {
        return id;
    }

    void setValue(String newValue) { value = newValue; }
    String getValue() { return value; }

    void setBindable(boolean canBind) { bindable = canBind; }
    boolean getBindable() { return bindable; }
}
