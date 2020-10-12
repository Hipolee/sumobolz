package com.example.juan.sumobolz;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.juan.sumobolz.managers.AppConstants;
import com.example.juan.sumobolz.model.BackgroundMusic;

import static android.content.Context.SENSOR_SERVICE;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    BackgroundMusic musicHandle;
    SumoSurface mSurface;

    private SensorManager sensorManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AppConstants.Initialization(this.getApplicationContext());

        // Sets the activity view as GameView class
        mSurface = new SumoSurface(this);

        // Get a reference to a SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        setContentView(mSurface);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        touched(event);

        return false;
    }

    private void touched(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        Log.i("INFO_TOUCH", "X: " + x + "  Y: " + y);
        if (event.getAction() == MotionEvent.ACTION_UP)
            AppConstants.GetEngine().createNewBall(x, y);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            //Set sensor values as acceleration
            float x = -event.values[2];
            float y = -event.values[1];

            // Log.i("INFO_MOTION", "X: " + x + "  Y: " + y);
            AppConstants.GetEngine().SetLastTouch(x, y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    protected void onStop(){
        super.onStop();
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (musicHandle == null){
            musicHandle = BackgroundMusic.getInstance(getApplicationContext());
        }
        musicHandle.playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
        musicHandle.pauseMusic();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        if (musicHandle == null){
            musicHandle = BackgroundMusic.getInstance(getApplicationContext());
        }
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
        musicHandle.getMusic().seekTo(musicHandle.getMusicCurrentTime());
        musicHandle.playMusic();
    }
}