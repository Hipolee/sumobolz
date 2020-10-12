package com.example.juan.sumobolz.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 23/03/2018.
 */

public class BluetoothFinderFragment extends android.support.v4.app.DialogFragment {

    List<BluetoothDevice> BluetoothDeviceList;

    public interface BluetoothFinderListener{
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    BluetoothFinderListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();
        Set<BluetoothDevice> pairedServices = bluetoothAdapter.getBondedDevices();
        BluetoothDeviceList = new ArrayList<>();

        CharSequence[] array = null;

        if(pairedServices.size() > 0){

            array = new CharSequence[pairedServices.size()];
            int i = 0;

            for(BluetoothDevice device : pairedServices){
                BluetoothDeviceList.add(device);
                array[i] = device.getName() + "\n" + device.getAddress();
                i++;
            }
            Log.wtf("debug","fenetre lancée");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Appareils Bluetooth")
                .setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.wtf("debug","Je clique sur ce satané bouton");
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (BluetoothFinderListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
}
