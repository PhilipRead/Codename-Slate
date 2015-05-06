package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 3/9/15.
 */
public class StorageNode extends BackendNode
{

    StorageNode(int id)
    {
        super(id);
        setBindable(true);
    }

    StorageNode(int id, String initialValue, boolean isNumber)
    {
        super(id, initialValue, isNumber);
        setBindable(true);
    }
}