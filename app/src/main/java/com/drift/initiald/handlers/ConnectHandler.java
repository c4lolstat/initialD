package com.drift.initiald.handlers;

import android.widget.CompoundButton;

import com.drift.initiald.SharedResources;
import com.drift.initiald.threads.BluetoothComm;

/**
 * Created by Farkas on 2017.10.14..
 */

public class ConnectHandler implements CompoundButton.OnCheckedChangeListener{

    private BluetoothComm btComm;
    private SharedResources resources;

    public ConnectHandler(BluetoothComm btComm, SharedResources resources){
        this.btComm = btComm;
        this.resources = resources;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!resources.isRun()){
            resources.clearSendBuffer();
            resources.clearReceiveBuffer();
            resources.executeThread(btComm);
            compoundButton.setChecked(true);
        }else{
            resources.setRun(false);
            compoundButton.setChecked(false);
        }
    }
}
