package com.nineya.cspeed.recorder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsongwang
 * @date 2020/8/23
 * 用于多线程记录的记录器
 */
public class MultiThreadRecorder extends AbstractRecorder {
    private Map<String, List<Long>> nums = new ConcurrentHashMap<String, List<Long>>();
    private Map<String, Long> startTimes = new ConcurrentHashMap<String, Long>();

    public MultiThreadRecorder(String name) {
        super(name);
    }

    @Override
    protected EveryPattern setEveryPattern() {
        return null;
    }

    @Override
    protected StatisticPattern setStatisticPattern() {
        return null;
    }

    @Override
    public Recorder start() {
        return null;
    }

    @Override
    public Recorder end() {
        return null;
    }

    @Override
    public void stop() {

    }
}
