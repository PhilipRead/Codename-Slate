package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 3/9/15.
 */
public class OutputNode extends BackendNode
{
    String text;

    OutputNode(String id)
    {
        super(id);
    }

    public void setText(String t)
    {
        text = t;
    }

    public String getText()
    {
        if(text == "REG_VAL")
        {
            InputNode node = (InputNode)BackendLogic.findNode("0");
            return node.getValue();
        }

        return text;
    }
}
