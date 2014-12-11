package com.example.zmotsing.myapplication.Backend;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * Created by zmotsing on 12/11/14.
 */
public class BackendNode {
    private String type; //type of node (ie input/output)
    private int param; //paramater (ie variable value)

    //default constructor
    public BackendNode()
    {
    }

    //override constructor
    public BackendNode(String t, int p)
    {
        type = t;
        param = p;
    }

    //print info to the console
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void output()
    {
        System.console().writer().println(type + " : " + param);
    }
}
