package com.drift.initiald;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.drift.initiald.handlers.BackwardHandler;
import com.drift.initiald.handlers.ServoHandler;
import com.drift.initiald.handlers.ThrottleHandler;
import com.drift.initiald.handlers.ConnectHandler;
import com.drift.initiald.threads.BluetoothComm;

public class RemoteControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(com.drift.initiald.R.layout.remote_control);

        SharedResources resources = SharedResources.getInstance();

        final BluetoothComm btComm = new BluetoothComm(resources);

        ((SeekBar) findViewById(com.drift.initiald.R.id.servo)).setOnSeekBarChangeListener(new ServoHandler(resources));
        ((SeekBar) findViewById(com.drift.initiald.R.id.throttle)).setOnSeekBarChangeListener(new ThrottleHandler(resources));
        ((ToggleButton) findViewById(com.drift.initiald.R.id.connect)).setOnCheckedChangeListener(new ConnectHandler(btComm, resources));
        ((ToggleButton) findViewById(com.drift.initiald.R.id.drive)).setOnCheckedChangeListener(new BackwardHandler(resources));
    }
}
