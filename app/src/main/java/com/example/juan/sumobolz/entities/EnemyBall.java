package com.example.juan.sumobolz.entities;

import android.graphics.Bitmap;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Juan on 20-Mar-18
 */

public class EnemyBall extends Ball {

    public EnemyBall(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    public EnemyBall(Bitmap bitmap, float x, float y) {
        super(bitmap, x, y);
    }

}
