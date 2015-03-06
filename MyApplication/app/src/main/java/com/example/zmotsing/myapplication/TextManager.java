package com.example.zmotsing.myapplication;


import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by pread on 12/10/14.
 */
public class TextManager {

    private float leftBound, rightBound, upBound, downBound, curX, curY;

    private CopyOnWriteArrayList<TextObject> TextList = new CopyOnWriteArrayList<>();

    public TextManager(float _leftBound, float _rightBound, float _upBound, float _downBound) {
        leftBound = _leftBound;
        rightBound = _rightBound;
        upBound = _upBound;
        downBound = _downBound;
        curX = _leftBound;
        curY = _upBound;
    }

    public void addText(String newText) {
        for (int i = 0; i < newText.length(); i++) {
            TextList.add(new TextObject(new Coord(curX, curY), newText.charAt(i)));
            curX += .35;
        }
    }

    public CopyOnWriteArrayList<TextObject> getTextList() {
        return TextList;
    }

}
