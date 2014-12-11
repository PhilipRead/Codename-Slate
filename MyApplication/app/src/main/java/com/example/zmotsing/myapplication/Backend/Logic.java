package com.example.zmotsing.myapplication.Backend;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zmotsing on 12/11/14.
 */
public class Logic {

    static CopyOnWriteArrayList<BackendNode> logicNodes = new CopyOnWriteArrayList<>(); //list of backend nodes

    //default constructor
    public Logic()
    {
    }

    //create a new backend node
    public void addLogicNode(String type, int param)
    {
        BackendNode newNode = new BackendNode(type, param);
        logicNodes.add(newNode);
    }

    //iterate through the backend nodes
    public void execute()
    {
        for(BackendNode nd : logicNodes)
        {
            nd.output();
        }
    }
}
