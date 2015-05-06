package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 3/9/15.
 */
public class OutputNode extends BackendNode
{
    BackendNode boundNode;

    OutputNode(int id, String newValue)
    {
        super(id, newValue, false);
        boundNode = null;
        setBindable(false);
    }

    OutputNode(int id, BackendNode newBoundNode)
    {
        super(id);
        boundNode = newBoundNode;
        setBindable(false);
    }

    public void bind(BackendNode node)
    {
        boundNode = node;
    }

    public String getValue()
    {
        if(boundNode != null)
        {
            return boundNode.getValue();
        }

        return super.getValue();
    }
}
