package com.drift.initiald.handlers;

import android.widget.SeekBar;

import com.drift.initiald.SharedResources;

/**
 * Created by Farkas on 2017.10.13..
 */

public class ServoHandler implements SeekBar.OnSeekBarChangeListener {

    private final float SLIDER_SERVO_RATIO = 31.0f/50.0f;
    private final byte SERVO_OFFSET = 32;
    private final int SLIDER_NULL_POINT = 50;
    private SharedResources resources;

    public ServoHandler(SharedResources resources) {
        this.resources = resources;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int value = i - SLIDER_NULL_POINT;

        if (value > 0){
            byte position = (byte)(value*SLIDER_SERVO_RATIO);
            resources.offerForSendBuffer(position + SERVO_OFFSET);
        }else{
            byte position = (byte)(value * SLIDER_SERVO_RATIO);
            resources.offerForSendBuffer(-1 * position);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
