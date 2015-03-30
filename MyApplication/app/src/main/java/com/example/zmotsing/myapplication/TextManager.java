package com.example.zmotsing.myapplication;


import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by pread on 12/10/14.
 */
public class TextManager {

    private float leftBound, rightBound, upBound, downBound, origX, curX, origY, curY;

    private CopyOnWriteArrayList<TextObject> TextList = new CopyOnWriteArrayList<>();

    public TextManager(float _leftBound, float _rightBound, float _upBound, float _downBound) {
        leftBound = _leftBound;
        rightBound = _rightBound;
        upBound = _upBound;
        downBound = _downBound;
        curX = origX = _leftBound;
        curY = origY = _upBound;
    }

    public TextObject [] addText(String newText) {
        TextObject [] tempArr = new TextObject[newText.length()];
        for (int i = 0; i < newText.length(); i++) {
            TextObject tempObj = new TextObject(new Coord (curX, curY), newText.charAt(i));
            tempArr[i] = tempObj;
            TextList.add(tempObj);
            curX += .35;
        }

        return tempArr;
    }

    public void clear() {
        TextList.clear();
        curX = origX;
        curY = origY;
    }

    public CopyOnWriteArrayList<TextObject> getTextList() {
        return TextList;
    }

}
