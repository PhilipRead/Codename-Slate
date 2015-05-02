package com.example.zmotsing.myapplication.Nodes;

import android.os.Handler;
import android.util.Log;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.LineStrip;
import com.example.zmotsing.myapplication.MyGLRenderer;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import org.w3c.dom.NodeList;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by acowdrey on 12/10/14.
 */
public class TravelingNode extends Node {
    public Node curNode;
    public LineStrip tLineStrip = null;
    public CopyOnWriteArrayList<Node> tNodeList;
    Handler myHandler;// = new Handler();
    Runnable myRunnable;
    public int ArrayIndex;
    Timer nodeTimer;
    public TravelingNode(Coord c, CopyOnWriteArrayList<Node> list, LineStrip strip ) {
        super(c,null,null);
        tLineStrip = strip;
        tNodeList = list;
        drawableInt = R.drawable.outputnode;
        ArrayIndex = 0;
    }

    @Override
    public void action(MyGLSurfaceView SV)
    {
        if((tLineStrip!= null) && tLineStrip.vertices.length > ArrayIndex)
        {

            //Log.w("ArrayIndex", "" + ArrayIndex);
            if((ArrayIndex%177)-3== 0)
            {
                curNode =tNodeList.get(ArrayIndex/177);
                curNode.action(SV);
            }
            float x = tLineStrip.vertices[ArrayIndex];
            float y = tLineStrip.vertices[ArrayIndex + 1];
            Coord c = new Coord(x, y);
            this.setCoord(c);
            spr.SetupSprite(R.drawable.travellingnode, x, y);
            ArrayIndex += 3;

        }
    }


    public void stop()
    {
        nodeTimer.cancel();
        nodeTimer.purge();
    }

    public void start()
    {
        nodeTimer = new Timer();
        nodeTimer.schedule(new TimerTask() {
            @Override
            public void run() {AdvanceTravelingNode();}
        }, 0, 10);
    }

    private void AdvanceTravelingNode() {
        myHandler.post(myRunnable);
    }

    public void setHandler(Runnable r, Handler h)
    {
        myRunnable = r;
        myHandler = h;
    }


    @Override
    public int getTextureIndex() {
        return 0;
    }

    @Override
    public void setSprite() {
        spr = new Sprite(R.drawable.travellingnode, super.getCoord().X, super.getCoord().Y, 0.02f * 4, 0.01333f * 4);
        //spr.printcoord = true;
    }


    @Override
    public String getTitle() {
        return "output";
    }
}
