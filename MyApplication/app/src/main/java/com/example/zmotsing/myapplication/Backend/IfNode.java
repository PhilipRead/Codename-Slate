package com.example.zmotsing.myapplication.Backend;

import android.util.Log;

/**
 * Created by zmotsing on 3/9/15.
 */
public class IfNode extends TripleNode
{
    IfNode(int id)
    {
        super(id);
    }

    public boolean computeValue()
    {
        if(leftBind != null)
            leftHasNum = leftBind.isNumeric();
        if(rightBind != null)
            rightHasNum = rightBind.isNumeric();

        if(leftHasNum != rightHasNum)
            return false;

        boolean tempValue;

        if(leftHasNum)
        {
            double leftOperand, rightOperand;

            if(leftBind != null)
                leftOperand = Double.parseDouble(leftBind.getValue());
            else
                leftOperand = Double.parseDouble(leftConstant);

            if(rightBind != null)
                rightOperand = Double.parseDouble(rightBind.getValue());
            else
                rightOperand = Double.parseDouble(rightConstant);

            if(operator.equals("=="))
                tempValue = (leftOperand == rightOperand);
            else if(operator.equals("!="))
                tempValue = (leftOperand != rightOperand);
            else if(operator.equals(">"))
                tempValue = (leftOperand > rightOperand);
            else if(operator.equals(">="))
                tempValue = (leftOperand >= rightOperand);
            else if(operator.equals("<"))
                tempValue = (leftOperand < rightOperand);
            else // <=
                tempValue = (leftOperand <= rightOperand);
        }
        else // Strings
        {
            String leftOperand, rightOperand;

            if(leftBind != null)
                leftOperand = leftBind.getValue();
            else
                leftOperand = leftConstant;

            if(rightBind != null)
                rightOperand = rightBind.getValue();
            else
                rightOperand = rightConstant;

            int tempResult = leftOperand.compareTo(rightOperand);

            if(operator.equals("=="))
                tempValue = (tempResult == 0);
            else if(operator.equals("!="))
                tempValue = (tempResult != 0);
            else if(operator.equals(">"))
                tempValue = (tempResult > 0);
            else if(operator.equals(">="))
                tempValue = (tempResult >= 0);
            else if(operator.equals("<"))
                tempValue = (tempResult < 0);
            else // <=
                tempValue = (tempResult <= 0);
        }

        if(tempValue)
            setValue("true", false);
        else
            setValue("false", false);

        return true;
    }
}
