package com.example.juan.sumobolz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.juan.sumobolz.managers.AppConstants;


/**
 * Created by Juan on 04-Feb-18
 */

public class SumoSurface extends SurfaceView implements SurfaceHolder.Callback {


    /**
     * The thread charged of displaying things
     */
    DisplayThread mThread;

    Context mContext;

    // Change the dimensions of the view, how many pixels we want to use...
    SurfaceHolder mHolder;


    public SumoSurface(Context context) {
        super(context);
        mHolder = getHolder();
        mContext = context;
        Init();
    }

    private void Init() {
        mHolder.addCallback(this);

        mThread = new DisplayThread(mHolder, mContext);
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Starts the display thread
        if (!mThread.IsRunning()) {
            mThread = new DisplayThread(getHolder(), mContext);
            mThread.start();
        } else {
            mThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // DO NOTHING
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Stop the display thread
        mThread.SetIsRunning(false);
        AppConstants.StopThread(mThread);
    }
}
