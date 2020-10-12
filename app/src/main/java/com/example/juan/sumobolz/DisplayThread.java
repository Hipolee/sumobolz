package com.example.juan.sumobolz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

import com.example.juan.sumobolz.entities.Ball;
import com.example.juan.sumobolz.managers.AppConstants;

/**
 * Created by Juan on 11-Feb-18
 */

public class DisplayThread extends Thread {
    private final SurfaceHolder mSurfaceHolder;
    private Paint mBackgroundPaint;


    //Delay amount between screen refreshes
    final long DELAY = 4;

    boolean mCanRun;

    public DisplayThread(SurfaceHolder surfaceHolder, Context context) {
        mSurfaceHolder = surfaceHolder;

        //black painter below to clear the screen before the game is rendered
        Paint blue = new Paint();
        blue.setColor(Color.parseColor("#7cb342"));
        blue.setStyle(Paint.Style.FILL);
        mBackgroundPaint = blue;

        mCanRun = true;
    }

    /**
     * This is the main nucleus of our program.
     * From here will be called all the methods that are associated with the display in GameEngine object
     */
    @Override
    public void run() {
        while (mCanRun) {
            // Do canvas drawing

            // Update the game's logic
            AppConstants.GetEngine().Update();

            // Locking the canvas
            Canvas canvas = mSurfaceHolder.lockCanvas(null);

            if (canvas != null) {

                // Clears the screen with black paint and draws object on the canvas
                synchronized (mSurfaceHolder) {

                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);

                    drawText("You're controlling the koala ball", canvas);

                    // Draw new positions and events on the screen
                    AppConstants.GetEngine().Draw(canvas);
                }

                // Unlocking the Canvas
                mSurfaceHolder.unlockCanvasAndPost(canvas);

                //Delay time
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException ex) {
                    //TODO: Log
                }


            }
        }
    }

    /**
     * Draws the desired text in the canvas
     *
     * @param s - The text
     * @param c - The canvas
     */
    private void drawText(String s, Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(52);
        paint.setTypeface(Typeface.create("Roboto", Typeface.NORMAL));


        c.drawText(s, AppConstants.SCREEN_WIDTH / 6.5f, AppConstants.SCREEN_HEIGHT - 150, paint);
    }

    /**
     * @return whether the thread is running
     */
    public boolean IsRunning() {
        return mCanRun;
    }

    /**
     * Sets the thread state, false = stoped, true = running
     **/
    public void SetIsRunning(boolean state) {
        mCanRun = state;
    }
}
