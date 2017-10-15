package com.drift.initiald.handlers;

import android.widget.SeekBar;

import com.drift.initiald.SharedResources;

/**
 * Created by Farkas on 2017.10.14..
 */

public class ThrottleHandler implements SeekBar.OnSeekBarChangeListener {

    private final float SLIDER_THROTTLE_RATIO = 63.0f/100.0f;
    private final byte THROTTLE_CHANNEL_OFFSET = 64;
    private SharedResources resources;

    public ThrottleHandler(SharedResources resources) {
        this.resources = resources;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        byte position = (byte)(i * SLIDER_THROTTLE_RATIO);
        if (!resources.isBackward()){
            resources.offerForSendBuffer(position + THROTTLE_CHANNEL_OFFSET);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
