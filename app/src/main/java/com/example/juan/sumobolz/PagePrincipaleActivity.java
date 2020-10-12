package com.example.juan.sumobolz;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.juan.sumobolz.dialog.BluetoothFinderFragment;
import com.example.juan.sumobolz.model.BackgroundMusic;

public class PagePrincipaleActivity extends AppCompatActivity implements BluetoothFinderFragment.BluetoothFinderListener {

    BackgroundMusic musicHandle;
    int currentTime;
    private final static int ACCESS_COARSE_LOCATION_CODE = 2;
    private final static int BLUETOOTH_ADMIN_REQUEST = 1;
    private static final int REQUEST_ENABLE_BT = 1;

    public void showNoticeDialog(){
        BluetoothFinderFragment dialog = new BluetoothFinderFragment();
        dialog.show(getSupportFragmentManager(),"BluetoothFinderFragment");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_principale);
        musicHandle = BackgroundMusic.getInstance(getApplicationContext());
        Button soloPlayer = findViewById(R.id.soloStart);
        Button multiPlay = findViewById(R.id.multiplayer);
        Button options = findViewById(R.id.settings);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        soloPlayer.setText(R.string.soloStart);
        multiPlay.setText(R.string.multiplayer);
        options.setText(R.string.settings);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(PagePrincipaleActivity.this,SettingsPageActivity.class);
                startActivity(switchPage);
            }
        });

        soloPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(PagePrincipaleActivity.this,MainActivity.class);
                startActivity(startGame);
            }
        });

        multiPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // Device doesn't support Bluetooth
                }
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                else {
                    checkAllPermission();
                }

                if (mBluetoothAdapter.isEnabled()) {
                    BluetoothFinderFragment newFragment = new BluetoothFinderFragment();
                    newFragment.show(getSupportFragmentManager(), "Bluetooth");
                }
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
            }
        }
    };

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

    protected void checkAllPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.wtf("debug","permission refusée");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},ACCESS_COARSE_LOCATION_CODE);
        }
        else Log.wtf("debug","permission accordée");
    }

    @Override
    public void onDialogPositiveClick(android.app.DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(android.app.DialogFragment dialog) {

    }
}
