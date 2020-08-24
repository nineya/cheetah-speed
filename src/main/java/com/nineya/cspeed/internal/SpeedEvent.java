package com.nineya.cspeed.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public class SpeedEvent {
    private final String name;
    private final long startTime;
    private long runTime;
    private long currentTime;
    private String threadName;
    private int num;
    private Map<String, Object> datas = new HashMap<>();

    public SpeedEvent(String name, long startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    public void setEndTime(long endTime){
        currentTime = System.currentTimeMillis();
        runTime = endTime - startTime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public long getRunTime() {
        return runTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public Object getData(String name){
        return datas.get(name);
    }

    public void addData(String name, Object obj){
        datas.put(name, obj);
    }

    public String getThreadName() {
        if (threadName == null){
            threadName = Thread.currentThread().getName();
        }
        return threadName;
    }
}
