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

    static {
        letterTextures = new HashMap<Character, Integer>();
        letterTextures.put('A', R.drawable.cap_a);
        letterTextures.put('B', R.drawable.cap_b);
        letterTextures.put('C', R.drawable.cap_c);
        letterTextures.put('D', R.drawable.cap_d);
        letterTextures.put('E', R.drawable.cap_e);
        letterTextures.put('F', R.drawable.cap_f);
        letterTextures.put('G', R.drawable.cap_g);
        letterTextures.put('H', R.drawable.cap_h);
        letterTextures.put('I', R.drawable.cap_i);
        letterTextures.put('J', R.drawable.cap_j);
        letterTextures.put('K', R.drawable.cap_k);
        letterTextures.put('L', R.drawable.cap_l);
        letterTextures.put('M', R.drawable.cap_m);
        letterTextures.put('N', R.drawable.cap_n);
        letterTextures.put('O', R.drawable.cap_o);
        letterTextures.put('P', R.drawable.cap_p);
        letterTextures.put('Q', R.drawable.cap_q);
        letterTextures.put('R', R.drawable.cap_r);
        letterTextures.put('S', R.drawable.cap_s);
        letterTextures.put('T', R.drawable.cap_t);
        letterTextures.put('U', R.drawable.cap_u);
        letterTextures.put('V', R.drawable.cap_v);
        letterTextures.put('W', R.drawable.cap_w);
        letterTextures.put('X', R.drawable.cap_x);
        letterTextures.put('Y', R.drawable.cap_y);
        letterTextures.put('Z', R.drawable.cap_z);
        letterTextures.put('0', R.drawable.num_0);
        letterTextures.put('1', R.drawable.num_1);
        letterTextures.put('2', R.drawable.num_2);
        letterTextures.put('3', R.drawable.num_3);
        letterTextures.put('4', R.drawable.num_4);
        letterTextures.put('5', R.drawable.num_5);
        letterTextures.put('6', R.drawable.num_6);
        letterTextures.put('7', R.drawable.num_7);
        letterTextures.put('8', R.drawable.num_8);
        letterTextures.put('9', R.drawable.num_9);
    }

    public TextObject(Coord _c, char _letter) {
        spr = new Sprite(letterTextures.get(_letter), _c.X, _c.Y, 0.2f, 0.1333f);
        c = _c;
        letter = _letter;
    }
}
