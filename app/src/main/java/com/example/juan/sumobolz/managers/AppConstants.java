package com.example.juan.sumobolz.managers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.juan.sumobolz.GameEngine;
import com.example.juan.sumobolz.entities.Ball;
import com.example.juan.sumobolz.entities.Sprite;
import com.example.juan.sumobolz.helpers.BotsDifficulty;
import com.example.juan.sumobolz.helpers.GameSpeed;

/**
 * Created by Juan on 11-Feb-18
 */

/**
 * A class containing the constants of this game
 */
public class AppConstants {

    private static BitmapBank mBitmapBank;
    private static GameEngine mEngine;
    private static Sprite mMainPlayer;

    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    /**
     * Initiates the application constants
     */
    public static void Initialization(Context context) {

        mBitmapBank = new BitmapBank(context.getResources());
        SetScreenSize(context);
        mEngine = new GameEngine(new SpriteManager(), BotsDifficulty.VERY_HARD, GameSpeed.NORMAL);

        Ball ball = new Ball(AppConstants.GetBitmapsBank().getKoalaBall());
        ball.addVelocity(0, 0);
        ball.setPosition(500, 100);
        mMainPlayer = ball;
        AppConstants.GetEngine().addBall(ball);
    }


    /**
     * Sets screen size constants accordingly to device screen size
     */
    private static void SetScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        AppConstants.SCREEN_WIDTH = width;
        AppConstants.SCREEN_HEIGHT = height;
    }

    /**
     * @return BitmapBank instance
     */
    public static BitmapBank GetBitmapsBank() {
        return mBitmapBank;
    }

    /**
     * @return GameEngine instance
     */
    public static GameEngine GetEngine() {
        return mEngine;
    }


    /**
     * Stops the given thread
     *
     * @param thread thread to stop
     */
    public static void StopThread(Thread thread) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Sprite getmMainPlayer() {
        return mMainPlayer;
    }
}