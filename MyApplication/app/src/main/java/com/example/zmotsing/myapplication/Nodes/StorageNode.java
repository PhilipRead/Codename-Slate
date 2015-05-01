package com.example.zmotsing.myapplication.Nodes;

import android.util.Log;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.MyGLRenderer;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

/**
 * Created by wbs on 5/1/15.
 */
public class StorageNode extends Node {
    int currentNodeIndex;

    public StorageNode(Coord c) {
        super(c);
        drawableInt = R.drawable.storagenode;
        currentNodeIndex = 0;
        scalingFactor = .2f;
        AddToLine = 1;
    }

    @Override
    public void action(MyGLSurfaceView SV) {
        Log.w("NODEACTION", "STORAGE");
        //MyGLRenderer.Tn.stop();
        //SV.getOutput(this.getID());
    }

    @Override
    protected void setSprite() {
        spr = new Sprite(drawableInt, co.X, co.Y, Width, Height);
        setBounds();
    }


    @Override
    public int getTextureIndex() {
        return 0;
    }


    @Override
    public String getTitle() {
        return "storage";
    }
}