package com.nineya.cspeed.recorder;

import com.nineya.cspeed.internal.SpeedEvent;
import com.nineya.cspeed.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linsongwang
 * @date 2020/8/23
 * 基础的记录器，不可用于多线程记录
 */
public class SimpleRecorder extends AbstractRecorder {
    private List<Long> nums = new ArrayList<Long>();
    private SpeedEvent event;

    public SimpleRecorder(String name) {
        super(name);
    }

    @Override
    protected EveryPattern setEveryPattern() {
        return (event)->{
            System.out.println(String.format("[%s]\t[%s - %s] - 第%s次 - %s",
                    StringUtil.getStringTime(event.getCurrentTime()),
                    event.getName(),
                    event.getThreadName(),
                    event.getNum(),
                    StringUtil.nsPattern(event.getRunTime())));
        };
    }

    @Override
    protected StatisticPattern setStatisticPattern() {
        return (name, list) -> {
            System.out.println(name + " 统计结果：");
        };
    }

    @Override
    public Recorder start() {
        event = new SpeedEvent(getName(), System.nanoTime());
        return this;
    }

    @Override
    public Recorder end() {
        event.setEndTime(System.nanoTime());
        event.setNum(nums.size() + 1);
        nums.add(event.getRunTime());
        getEveryPattern().print(event);
        return this;
    }

    @Override
    public void stop() {
        getStatisticPattern().print(getName(), nums);
    }
}
