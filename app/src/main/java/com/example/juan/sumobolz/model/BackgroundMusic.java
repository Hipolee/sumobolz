package com.example.juan.sumobolz.model;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.juan.sumobolz.R;

/**
 * Created by lubourzier on 13/03/18.
 */

public class BackgroundMusic {
    private volatile static BackgroundMusic instanceUnique;
    private MediaPlayer music;
    private int currentDuration = 0;

    private BackgroundMusic(Context context){
        music = MediaPlayer.create(context, R.raw.gamemusic);
        music.start();
        music.setLooping(true);
        currentDuration = music.getCurrentPosition();

    }

    public static BackgroundMusic getInstance(Context context){
        if (instanceUnique == null){
            instanceUnique = new BackgroundMusic(context);
        }
        return instanceUnique;
    }

    public MediaPlayer getMusic(){
        return music;
    }

    public int getMusicCurrentTime(){
        return music.getCurrentPosition();
    }

    public void setMusicCurrentTime(int time){
        this.currentDuration = time;
    }

    public void pauseMusic(){
        music.pause();
    }

    public void stopMusic(){
        music.stop();
        music.release();
    }

    public void playMusic(){
        music.start();
    }
}
