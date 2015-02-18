package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 12/11/14.
 */
public class VariableNode {
    private String name;
    private int value;

    public VariableNode()
    {
    }

    public VariableNode(String n, int v)
    {
        name = n;
        value = v;
    }

    public String getName()
    {
        return name;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int v)
    {
        value = v;
    }

    //return string representation of the variable and its value
    public String output()
    {
        return (name + " : " + value);
    }
}
