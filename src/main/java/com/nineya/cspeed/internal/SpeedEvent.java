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
    private int count;
    private Map<String, Object> datas = new HashMap<>();

    /**
     * 实例化，通过计速器名称和开始时间
     * @param name 计速器名称
     * @param startTime 开始时间
     */
    public SpeedEvent(String name, long startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    /**
     * 设置结束时间，计算运行时间
     * @param endTime 结束时间
     */
    public void setEndTime(long endTime){
        currentTime = System.currentTimeMillis();
        runTime = endTime - startTime;
    }

    /**
     * 取得记录数
     * @return 记录数
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置记录数
     * @param count 记录数
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 取得计速器名称
     * @return 计速器名称
     */
    public String getName() {
        return name;
    }

    /**
     * 取得运行时间
     * @return 运行时间
     */
    public long getRunTime() {
        return runTime;
    }

    /**
     * 取得当前时间，当前时间即在endStart被调用的时间，毫秒单位
     * @return 当前时间
     */
    public long getCurrentTime() {
        return currentTime;
    }

    /**
     * 取得data数据，如果有其他数据在这里没有明确定义，可以添加到data中进行记录
     * @param name 记录名称
     * @return 记录内容
     */
    public Object getData(String name){
        return datas.get(name);
    }

    /**
     * data添加一条记录
     * @param name 记录名称
     * @param obj 记录内容
     */
    public void addData(String name, Object obj){
        datas.put(name, obj);
    }

    /**
     * 取得当前线程名称
     * @return 当前线程名称
     */
    public String getThreadName() {
        if (threadName == null){
            threadName = Thread.currentThread().getName();
        }
        return threadName;
    }
}
