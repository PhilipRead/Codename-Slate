package com.example.zmotsing.myapplication.Backend;

/**
 * Created by pread on 5/4/15.
 */
public class SetNode extends BackendNode {

    protected StorageNode storeNode;
    protected BackendNode rBind;

    SetNode(int id)
    {
        super(id);
        bindable = false;
        storeNode = null;
        rBind = null;
    }


    public void setStoreNode(StorageNode sNode)
    {
        storeNode = sNode;
    }

    public void set_rBind(BackendNode rightBind)
    {
        rBind = rightBind;
    }

    public void doSet()
    {
        if(rBind != null)
        {
            storeNode.setValue(rBind.getValue(), rBind.isNumeric());
        }
        else //Constant value specified
        {
            storeNode.setValue(this.value, this.hasNumericValue);
        }
    }
}
