package com.drift.initiald.handlers;

import android.widget.CompoundButton;

import com.drift.initiald.SharedResources;

/**
 * Created by Farkas on 2017.10.14..
 */

public class BackwardHandler implements CompoundButton.OnCheckedChangeListener{

    private final int CHANNEL_OFFSET = 192;
    private final int BACKWARD_CODE = 11;
    private final int FORWARD_CODE = 52;
    private SharedResources resources;


    public BackwardHandler(SharedResources resources) {
        this.resources = resources;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!resources.isBackward()){
            resources.setBackward(true);
            resources.offerForSendBuffer(CHANNEL_OFFSET + BACKWARD_CODE); //channel 3 offset 192
            compoundButton.setChecked(true);
        }else{
            resources.setBackward(false);
            resources.offerForSendBuffer(CHANNEL_OFFSET + FORWARD_CODE);
            compoundButton.setChecked(false);
        }
    }
}
