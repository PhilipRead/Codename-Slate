package com.example.zmotsing.myapplication.Nodes;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.MyGLRenderer;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

/**
 * Created by acowdrey on 3/30/15.
 */
public class BackgroundNode extends Node {

    public int ArrayIndex;

    public BackgroundNode(Coord c) {
        super(c);
        drawableInt = R.drawable.outputnode;
        ArrayIndex = 0;
    }

    @Override
    public void action(MyGLSurfaceView SV) {
        if (MyGLRenderer.linestrip.vertices.length > ArrayIndex) {
            float x = MyGLRenderer.linestrip.vertices[ArrayIndex];
            float y = MyGLRenderer.linestrip.vertices[ArrayIndex + 1];
            Coord c = new Coord(x, y);
            this.setCoord(c);
            spr.SetupSprite(R.drawable.background, x, y);
            ArrayIndex += 3;

        }
    }


    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    public void setSprite() {
        spr = new Sprite(R.drawable.background, super.getCoord().X, super.getCoord().Y, 0.02f * 4, 0.01333f * 4);
        //spr.printcoord = true;
    }


    @Override
    public String getTitle() {
        return "background";
    }
}