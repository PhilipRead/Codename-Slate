package com.example.zmotsing.myapplication.Backend;

import com.example.zmotsing.myapplication.Nodes.*;

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

    public static void updateBackendNode(int id, String text)
    {
        BackendNode node = findNode(id);

        node.setValue(text);
    }

//Input Methods
    public static void initializeInputNode(int id)
    {
        InputNode node = new InputNode(id);
        logicNodes.add(node);
    }

//Output Methods
    public static void initializeOutputNode(int id, int bindNodeID)
    {
        BackendNode tempBind = findNode(bindNodeID);
        OutputNode node = new OutputNode(id, tempBind);
        logicNodes.add(node);
    }
    public static void initializeOutputNode(int id, String text)
    {
        OutputNode node = new OutputNode(id, text);
        logicNodes.add(node);
    }

    public static void bindOutputNode(int outID, int bindID)
    {
        OutputNode outNode = (OutputNode) findNode(outID);
        BackendNode childNode = findNode(bindID);

        outNode.bind(childNode);
    }

    public static String printOutputNode(int id)
    {
        OutputNode node = (OutputNode)findNode(id);
        return node.getValue();
    }

//Storage Methods
    public static void initializeStorageNode(int id)
    {
        StorageNode node = new StorageNode(id);
        logicNodes.add(node);
    }

    public static void initializeStorageNode(int id, String value, boolean isNumber)
    {
        StorageNode node = new StorageNode(id, value, isNumber);
        logicNodes.add(node);
    }
}
