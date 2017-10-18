package com.drift.initiald;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.drift.initiald.handlers.BackwardHandler;
import com.drift.initiald.handlers.ServoHandler;
import com.drift.initiald.handlers.ThrottleHandler;
import com.drift.initiald.handlers.ConnectHandler;
import com.drift.initiald.threads.BluetoothComm;

public class RemoteControl extends AppCompatActivity {

    final SharedResources resources = SharedResources.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(com.drift.initiald.R.layout.remote_control);
        final BluetoothComm btComm = new BluetoothComm(resources);

        ((SeekBar) findViewById(R.id.servo)).setOnSeekBarChangeListener(new ServoHandler(resources));
        ((SeekBar) findViewById(R.id.throttle)).setOnSeekBarChangeListener(new ThrottleHandler(resources));
        ((ToggleButton) findViewById(R.id.connect)).setOnCheckedChangeListener(new ConnectHandler(btComm, resources));
        ((ToggleButton) findViewById(R.id.drive)).setOnCheckedChangeListener(new BackwardHandler(resources));

        screenUpdate();
    }

    private void screenUpdate(){
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!resources.isReceiveBufferEmpty()){
                                    Integer code = resources.takeFromReceiveBuffer();
                                    switch(code){
                                        case 236:{
                                            findViewById(R.id.battery).setBackgroundColor(Color.RED);
                                            ((TextView) findViewById(R.id.battery)).setTextColor(Color.YELLOW);
                                            break;
                                        }
                                        default:
                                            break;
                                    }
                                }
                                if (!resources.isRun() && resources.isReceiveBufferEmpty()){
                                    findViewById(R.id.battery).setBackgroundColor(Color.argb(0,0,0,0));
                                    ((TextView) findViewById(R.id.battery)).setTextColor(Color.rgb(88,88,88));
                                }
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
