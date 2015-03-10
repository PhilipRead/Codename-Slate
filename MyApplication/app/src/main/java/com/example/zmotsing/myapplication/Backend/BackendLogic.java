package com.example.zmotsing.myapplication.Backend;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zmotsing on 12/11/14.
 */
public class BackendLogic
{
    private static CopyOnWriteArrayList<BackendNode> logicNodes = new CopyOnWriteArrayList<>();

    // Private Methods
    private static BackendNode findNode(String id)
    {
        BackendNode node = null;
        for (BackendNode nd : logicNodes)
        {
            if (nd.getId() == Integer.parseInt(id))
            {
                node = nd;
                break;
            }
        }

        return node;
    }

    // General Methods
    public static boolean addNode(String id, String type)
    {
        switch(type)
        {
            case "input":
                InputNode inpNode = new InputNode(id);
                logicNodes.add(inpNode);
                break;
            case "output":
                OutputNode outNode = new OutputNode(id);
                logicNodes.add(outNode);
                break;
            case "ifelse":
                IfElseNode ifElseNode = new IfElseNode(id);
                logicNodes.add(ifElseNode);
                break;
            default:
                return false;
        }

        return true;
    }

    // Input Nodes
    public static void initializeInputNode(String id, String name, String value)
    {
        InputNode node = (InputNode)findNode(id);
        node.Initialize(name, value);
    }

    public static void updateInputNode(String id, String operation, String value)
    {
        InputNode node = (InputNode)findNode(id);

        try {
            int intValue = Integer.parseInt(value);

            switch (operation) {
                case "+":
                    node.setIntValue(node.getIntValue() + intValue);
                    break;
                case "-":
                    node.setIntValue(node.getIntValue() - intValue);
                    break;
                case "*":
                    node.setIntValue(node.getIntValue() * intValue);
                    break;
                case "/":
                    node.setIntValue(node.getIntValue() / intValue);
                    break;
                default:
                    node.setIntValue(intValue);
            }
        }
        catch(NumberFormatException e)
        {
            switch(operation)
            {
                case "+":
                    node.setValue(node.getValue() + value);
                    break;
                default:
                    node.setValue(value);
            }
        }
    }

// Output Nodes

// If-Else Nodes

}
