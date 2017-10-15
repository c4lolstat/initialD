package com.drift.initiald;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Farkas on 2017.10.14..
 */

public class SharedResources {

    private static final SharedResources INSTANCE = new SharedResources();

    private boolean backward = false;
    private boolean run = false;
    private BlockingQueue<Integer> sendBuffer = new LinkedBlockingDeque<>();
    private BlockingQueue<Integer> receiveBuffer = new LinkedBlockingDeque<>();
    private final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            2,       // Initial pool size
            2,       // Max pool size
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());
    private boolean sendBufferEmpty;

    private SharedResources(){}

    public static SharedResources getInstance(){
        return INSTANCE;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public boolean isRun() {
        return run;
    }

    public boolean isSendBufferEmpty() {
        return sendBuffer.isEmpty();
    }

    public Integer takeFromSendBuffer() {
        Integer element = 0;
        try {
            element =  sendBuffer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return element;
    }

    public boolean isBackward() {
        return backward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public void offerForSendBuffer(Integer i) {
        sendBuffer.offer(i);
    }

    public void clearSendBuffer() {
        sendBuffer.clear();
    }

    public void clearReceiveBuffer() {
        receiveBuffer.clear();
    }

    public void executeThread(Runnable runnable) {
        THREAD_POOL.execute(runnable);
    }
}
