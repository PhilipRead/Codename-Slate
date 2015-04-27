package com.example.zmotsing.myapplication.Backend;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zmotsing on 12/11/14.
 */
public class BackendLogic
{
    private static CopyOnWriteArrayList<BackendNode> logicNodes = new CopyOnWriteArrayList<>();

// General Methods
    public static BackendNode findNode(int id)
    {
        BackendNode node = null;
        for (BackendNode nd : logicNodes)
        {
            if (nd.getId() == id)
            {
                node = nd;
                break;
            }
        }

        return node;
    }

// Register Methods
    public static void backendInitialize()
    {
        initializeVariableNode(0, "REG_VAL", "");
        initializeOutputNode(1, "");
        bindOutputNode(1, 0);
    }

    public static boolean updateRegister(String value)
    {
        return updateVariableNode(0, value);
    }

    public static String printRegister()
    {
        return printOutputNode(1);
    }

// Input Nodes
    public static void initializeVariableNode(int id, String name, String value)
    {
        VariableNode node = new VariableNode(id);
        node.Initialize(name, value);
        logicNodes.add(node);
    }

    public static void initializeVariableNode(int id, String name, double value)
    {
        VariableNode node = new VariableNode(id);
        node.Initialize(name, value);
        logicNodes.add(node);
    }

    public static boolean updateVariableNode(int id, String value)
    {
        BackendNode backNode = findNode(id);

        if(!(backNode instanceof VariableNode))
        {
            return false;
        }

        VariableNode varNode = (VariableNode)backNode;
        return varNode.setValue(value);
    }

    public static boolean updateVariableNode(int id, double value)
    {
        BackendNode backNode = findNode(id);

        if(!(backNode instanceof VariableNode))
        {
            return false;
        }

        VariableNode varNode = (VariableNode)backNode;
        return varNode.setValue(value);
    }

// Output Nodes
    public static void initializeOutputNode(int id, String text)
    {
        OutputNode node = new OutputNode(id);
        node.setValue(text);
        logicNodes.add(node);
    }

    public static boolean bindOutputNode(int outId, int varId)
    {
        BackendNode outBackNode = findNode(outId);
        BackendNode varBackNode = findNode(varId);

        if(!(outBackNode instanceof OutputNode) || !(varBackNode instanceof VariableNode))
        {
            return false;
        }

        OutputNode outNode = (OutputNode)outBackNode;
        VariableNode varNode = (VariableNode)varBackNode;

        outNode.bind(varNode);

        return true;
    }

    public static String printOutputNode(int id)
    {
        OutputNode node = (OutputNode)findNode(id);
        return node.getValue();
    }

// If-Else Nodes
}
