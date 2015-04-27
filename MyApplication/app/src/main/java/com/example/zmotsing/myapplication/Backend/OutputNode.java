package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 3/9/15.
 */
public class OutputNode extends BackendNode
{
    String text;
    VariableNode boundNode;

    OutputNode(int id)
    {
        super(id);
        boundNode = null;
    }

    public void setValue(String t)
    {
        text = t;
    }

    public void bind(VariableNode node)
    {
        boundNode = node;
    }

    public String getValue()
    {
        if(boundNode == null)
        {
            return boundNode.getStringValue();
        }

        return text;
    }
}
