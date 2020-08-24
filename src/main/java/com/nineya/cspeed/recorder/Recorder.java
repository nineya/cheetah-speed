package com.nineya.cspeed.recorder;

/**
 * @author linsongwang
 * @date 2020/8/24
 */
public interface Recorder {

    public abstract Recorder start();

    public abstract Recorder end();

    public abstract void stop();
}
