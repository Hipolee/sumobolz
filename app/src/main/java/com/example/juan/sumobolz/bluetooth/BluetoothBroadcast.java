package com.example.juan.sumobolz.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 24/03/2018.
 */

public class BluetoothBroadcast extends BroadcastReceiver {


    Set<BluetoothDevice> discoveredDevices = new HashSet<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress();
            discoveredDevices.add(device);


            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                //
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                //
            }
            affichageDevice(discoveredDevices);
        }
    }

    private void affichageDevice(Set<BluetoothDevice> devices) {
        if (devices.size() > 0) {


            Log.wtf("discoveredDevices", "discoveredDevices");


            for (BluetoothDevice device : devices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                Log.wtf("nom", device.getName());
                Log.wtf("Adress", device.getAddress());
                Log.wtf("classBT", device.getBluetoothClass().toString());


            }

        } else {
            Log.wtf("error", "pas de devices");
        }
    }
}
