package com.example.juan.sumobolz;

import android.app.Service;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.juan.sumobolz.model.BackgroundMusic;

public class SettingsPageActivity extends AppCompatActivity {

    BackgroundMusic musicHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        final AudioManager mediaTon = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        int maxVolume = mediaTon.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = mediaTon.getStreamVolume(AudioManager.STREAM_MUSIC);

        TextView son = findViewById(R.id.masterVolume);
        SeekBar setSon = findViewById(R.id.masterVolumeSetter);
        TextView couleur = findViewById(R.id.ballColor);
        SeekBar setcouleur = findViewById(R.id.setBallColor);
        mediaTon.setMode(AudioManager.STREAM_MUSIC);

        son.setText(R.string.masterVolume);
        couleur.setText(R.string.ballColor);
        setSon.setMax(maxVolume);
        setSon.setProgress(curVolume);

        setSon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                if(progress == 0) {
                    mediaTon.setStreamVolume(0,0,0);
                }
                else{
                    mediaTon.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    protected void onStop(){
        super.onStop();
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (musicHandle == null){
            musicHandle = BackgroundMusic.getInstance(getApplicationContext());
        }
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
        musicHandle.getMusic().seekTo(musicHandle.getMusicCurrentTime());
        musicHandle.playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
        musicHandle.pauseMusic();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (musicHandle == null){
            musicHandle = BackgroundMusic.getInstance(getApplicationContext());
        }
        musicHandle.setMusicCurrentTime(musicHandle.getMusicCurrentTime());
        musicHandle.getMusic().seekTo(musicHandle.getMusicCurrentTime());
    }
}
