package com.drift.initiald.threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.drift.initiald.SharedResources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Farkas on 2017.10.12..
 */

public class BluetoothComm implements Runnable {

    private final String ADDRESS = "30:14:08:29:04:47";
    private final String UUID_STRING = "00001101-0000-1000-8000-00805F9B34FB";
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream os = null;
    private SharedResources resources;

    public BluetoothComm(SharedResources resources) {
        this.resources = resources;
    }

    private void initDevice() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        device = adapter.getRemoteDevice(ADDRESS);
    }

    private void openConnection() {
        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID_STRING));
            socket.connect();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initDevice();
        openConnection();
        if (os != null){
            resources.setRun(true);
            while(resources.isRun()){
                if (!resources.isSendBufferEmpty()){
                    try {
                        os.write(resources.takeFromSendBuffer());
                        Thread.sleep(10);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
