package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 12/11/14.
 */

public class BackendNode
{
    private int id;
    private String value;
    private boolean bindable;
    private boolean hasNumericValue;

    BackendNode(int i)
    {
        id = i;
        value = null;
    }

    BackendNode(int i, String newValue, boolean isNumber)
    {
        id = i;
        value = newValue;
        hasNumericValue = isNumber;
    }

    int getId()
    {
        return id;
    }

    void setValue(String newValue, boolean isNumber)
    {
        value = newValue;
        hasNumericValue = isNumber;
    }

    String getValue() { return value; }

    void setBindable(boolean canBind) { bindable = canBind; }

    boolean getBindable() { return bindable; }

    public boolean isNumeric()
    {
        return hasNumericValue;
    }
}
