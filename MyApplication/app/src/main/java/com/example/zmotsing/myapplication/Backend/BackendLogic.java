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

    public static void updateBackendNode(int id, String text, boolean isNumber)
    {
        BackendNode node = findNode(id);

        node.setValue(text, isNumber);
    }

    public static void setBackendTrip(int parID, int leftID, String operator, int rightID)
    {
        TripleNode tempTrip = (TripleNode) findNode(parID);
        BackendNode tempLeft = findNode(leftID);
        BackendNode tempRight = findNode(rightID);

        tempTrip.setLeftBind(tempLeft);
        tempTrip.setRightBind(tempRight);
        tempTrip.setOperator(operator);
    }

    public static void setBackendTrip(int parID, String leftConst, boolean isLeftNum, String operator, int rightID)
    {
        TripleNode tempTrip = (TripleNode) findNode(parID);
        BackendNode tempRight = findNode(rightID);

        tempTrip.setLeftConstant(leftConst, isLeftNum);
        tempTrip.setRightBind(tempRight);
        tempTrip.setOperator(operator);
    }

    public static void setBackendTrip(int parID, int leftID, String operator, String rightConst, boolean isRightNum)
    {
        TripleNode tempTrip = (TripleNode) findNode(parID);
        BackendNode tempLeft = findNode(leftID);

        tempTrip.setLeftBind(tempLeft);
        tempTrip.setRightConstant(rightConst, isRightNum);
        tempTrip.setOperator(operator);
    }

    public static void setBackendTrip(int parID, String leftConst, boolean isLeftNum, String operator, String rightConst, boolean isRightNum)
    {
        TripleNode tempTrip = (TripleNode) findNode(parID);

        tempTrip.setLeftConstant(leftConst, isLeftNum);
        tempTrip.setRightConstant(rightConst, isRightNum);
        tempTrip.setOperator(operator);
    }

    public static String getBackendVal(int nodeID)
    {
        BackendNode node = findNode(nodeID);
        return node.getValue();
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

//If Methods
    public static void initializeIfNode(int id)
    {
        IfNode node = new IfNode(id);
        logicNodes.add(node);
    }

    public static boolean calculateIf(int id)
    {
        IfNode tempIf = (IfNode) findNode(id);

        return tempIf.computeValue();
    }

    public static boolean getIfBool(int id)
    {
        IfNode tempIf = (IfNode) findNode(id);

        if(tempIf.getValue().equals("true"))
            return true;
        else
            return false;
    }

//Math Methods
public static void initializeMathNode(int id)
{
    MathNode node = new MathNode(id);
    logicNodes.add(node);
}
}
