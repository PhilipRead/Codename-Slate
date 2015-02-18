package com.example.zmotsing.myapplication.Backend;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zmotsing on 12/11/14.
 */
public class Logic {
    private CopyOnWriteArrayList<VariableNode> logicNodes = new CopyOnWriteArrayList<>(); //list of backend nodes

    //default constructor
    public Logic()
    {
        //test code to be deleted later
        addVariableNode("x", 5);
        execute();
        updateVariableNode("+", "x", 5);
        execute();
    }

    //create a new variable node
    public void addVariableNode(String name, int value)
    {
        VariableNode newNode = new VariableNode(name, value);
        logicNodes.add(newNode);
    }

    //update variable node
    public void updateVariableNode(String operation, String name, int value)
    {
        VariableNode node = new VariableNode();
        boolean found = false;
        for(VariableNode nd : logicNodes)
        {
            if (nd.getName().equals(name))
            {
                node = nd;
                found = true;
                break;
            }
        }

        //error
        if(!found)
        {
            return;
        }

        switch(operation)
        {
            case "+":
                node.setValue(node.getValue() + value);
                break;
            case "-":
                node.setValue(node.getValue() - value);
                break;
            case "*":
                node.setValue(node.getValue() * value);
                break;
            case "/":
                node.setValue(node.getValue() / value);
                break;
            default:
                node.setValue(value);
                break;
        }
    }

    //iterate through the backend nodes
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void execute()
    {
        for(VariableNode nd : logicNodes)
        {
            System.out.println( nd.output() );
        }
    }
}
