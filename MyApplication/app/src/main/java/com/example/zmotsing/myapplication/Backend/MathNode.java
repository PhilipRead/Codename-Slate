package com.example.zmotsing.myapplication.Backend;

/**
 * Created by zmotsing on 5/4/15.
 */
public class MathNode extends TripleNode {
    MathNode(int id)
    {
        super(id);
        bindable = true;
    }

    public boolean computeValue()
    {
        if(leftBind != null)
            leftHasNum = leftBind.isNumeric();
        if(rightBind != null)
            rightHasNum = rightBind.isNumeric();

        if(leftHasNum != rightHasNum)
            return false;

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

            double tempResult;

            if(operator.equals("+"))
                tempResult = leftOperand + rightOperand;
            else if(operator.equals("-"))
                tempResult = leftOperand - rightOperand;
            else if(operator.equals("*"))
                tempResult = leftOperand * rightOperand;
            else if(operator.equals("/"))
                tempResult = leftOperand / rightOperand;
            else if(operator.equals("^"))
                tempResult = Math.pow(leftOperand, rightOperand);
            else if(operator.equals("%"))
                tempResult = leftOperand % rightOperand;
            else
                return false;

            setValue(Double.toString(tempResult), true);
        }
        else // Strings
        {
            if(!operator.equals("+"))
                return false;

            String leftOperand, rightOperand;

            if(leftBind != null)
                leftOperand = leftBind.getValue();
            else
                leftOperand = leftConstant;

            if(rightBind != null)
                rightOperand = rightBind.getValue();
            else
                rightOperand = rightConstant;

            String tempResult = leftOperand + rightOperand;

            setValue(tempResult, false);
        }

        return true;
    }
}
