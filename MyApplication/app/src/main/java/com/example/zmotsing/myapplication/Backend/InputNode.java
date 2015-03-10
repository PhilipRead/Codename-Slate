package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 3/9/15.
 */
public class InputNode extends BackendNode
{
    private String name;
    private String value;

    public InputNode(String id)
    {
        super(id);
    }

    public void Initialize(String n, String v)
    {
        name = n;
        value = v;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String v)
    {
        value = v;
    }

    public int getIntValue()
    {
        return Integer.parseInt(value);
    }

    public void setIntValue(int v)
    {
        value = Integer.toString(v);
    }
}