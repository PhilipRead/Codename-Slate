package com.example.zmotsing.myapplication.Nodes;

import android.os.Handler;
import android.util.Log;

import com.example.zmotsing.myapplication.Coord;
import com.example.zmotsing.myapplication.MyGLRenderer;
import com.example.zmotsing.myapplication.MyGLSurfaceView;
import com.example.zmotsing.myapplication.Node;
import com.example.zmotsing.myapplication.R;
import com.example.zmotsing.myapplication.Sprite;

import org.w3c.dom.NodeList;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by acowdrey on 12/10/14.
 */
public class TravelingNode extends Node {
    Handler myHandler;// = new Handler();
    Runnable myRunnable;
    public int ArrayIndex;
    Timer nodeTimer;
    public TravelingNode(Coord c) {
        super(c);
        drawableInt = R.drawable.outputnode;
        ArrayIndex = 0;
    }

    @Override
    public void action(MyGLSurfaceView SV)
    {
        if((MyGLRenderer.linestrip!= null) && MyGLRenderer.linestrip.vertices.length > ArrayIndex)
        {


            //Log.w("ArrayIndex", "" + ArrayIndex);
            if((ArrayIndex%177)-3== 0)
            {
                MyGLRenderer.NodeList.get(ArrayIndex/177).action(SV);
            }
            float x = MyGLRenderer.linestrip.vertices[ArrayIndex];
            float y = MyGLRenderer.linestrip.vertices[ArrayIndex + 1];
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
