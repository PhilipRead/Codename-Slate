package com.example.zmotsing.myapplication.Backend;

/**
 * Created by wbs on 5/6/15.
 */
public class TripleNode extends BackendNode
{
    protected BackendNode leftBind;
    protected BackendNode rightBind;
    protected String leftConstant;
    protected boolean leftHasNum;
    protected String rightConstant;
    protected boolean rightHasNum;
    protected String operator;

    TripleNode(int i)
    {
        super(i);
        leftBind = null;
        rightBind = null;
        operator = null;
    }

    public void setLeftBind(BackendNode lNode)
    {
        leftBind = lNode;
        leftHasNum = lNode.isNumeric();
    }

    public void setRightBind(BackendNode rNode)
    {
        rightBind = rNode;
        rightHasNum = rNode.isNumeric();
    }

    public void setLeftConstant(String lConst, boolean isLeftNum)
    {
        leftConstant = lConst;
        leftHasNum = isLeftNum;
    }

    public void setRightConstant(String rConst, boolean isRightNum)
    {
        rightConstant = rConst;
        rightHasNum = isRightNum;
    }

    public void setOperator(String newOperator)
    {
        operator = newOperator;
    }
}
