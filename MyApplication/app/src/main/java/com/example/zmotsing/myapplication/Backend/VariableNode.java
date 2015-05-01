package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 3/9/15.
 */
public class VariableNode extends BackendNode
{
    private String name;
    private String value;
    private boolean hasNumericValue;

    public VariableNode(int id)
    {
        super(id);
    }

    public void Initialize(String n, String v)
    {
        name = n;
        value = v;
        hasNumericValue = false;
    }

    public void Initialize(String n, double v)
    {
        name = n;
        value = Double.toString(v);
        hasNumericValue = true;
    }

    public String getName()
    {
        return name;
    }

   /* public boolean setValue(String v)
    {
        if(hasNumericValue)
        {
            return false;
        }

        value = v;
        return true;
    }*/

    public boolean setValue(double v)
    {
        if(!hasNumericValue)
        {
            return false;
        }

        value = Double.toString(v);
        return true;
    }

    public String getStringValue()
    {
        return value;
    }

    public double getNumericValue()
    {
        if (hasNumericValue)
        {
            return Double.parseDouble(value);
        }

        return 0;
    }
}