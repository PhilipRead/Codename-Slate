package com.example.zmotsing.myapplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pread on 12/10/14.
 */
public class TextObject {

    public Sprite spr;
    public Coord c;
    public char letter;

    private static final Map<Character, Integer> letterTextures;

    static
    {
        letterTextures = new HashMap<Character, Integer>();
        letterTextures.put('A', R.drawable.cap_a);
    }

    public TextObject(Coord _c, char _letter) {
        spr = new Sprite(letterTextures.get(_letter),_c.X,_c.Y);
        c = _c;
        letter = _letter;
    }
}
