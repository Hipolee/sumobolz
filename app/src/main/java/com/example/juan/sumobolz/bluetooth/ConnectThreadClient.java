package com.example.juan.sumobolz.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 24/03/2018.
 */

public class ConnectThreadClient extends Thread {

    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private static final String NAME = "TicTacToeApp";
    private static final UUID MY_UUID = UUID.fromString("87f84d8d-702d-4c77-a7a1-0f7c58c30b81");

    public ConnectThreadClient(BluetoothDevice device) {


        BluetoothSocket tmp = null;
        mmDevice = device;
        Log.wtf("debug","testConnect");
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "la méthode de création du socket à échoué", e);
        }
        Log.wtf("debug","testConnect2");
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        //mBluetoothAdapter.cancelDiscovery();
        Log.wtf("test","test1");
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Impossible de fermer le client", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        //manageMyConnectedSocket(mmSocket);
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Impossible de fermer le client", e);
        }
    }

}
