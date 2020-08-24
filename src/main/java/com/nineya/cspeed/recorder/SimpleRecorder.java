package com.nineya.cspeed.recorder;

import com.nineya.cspeed.internal.SpeedEvent;
import com.nineya.cspeed.util.StatisticsUtil;
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
        return new StatisticPattern<List<Long>>() {
            @Override
            public void print(String name, List<Long> list) {
                list.sort((a,b) -> (int) (a - b));
                StringBuilder sb = new StringBuilder(name + " 统计结果：");
                sb.append("\ncount: " + list.size());
                sb.append("\nmean: " + StringUtil.detailNs((long) StatisticsUtil.mean(list)));
                sb.append("\nmin: " + StringUtil.detailNs(list.get(0)));
                sb.append("\nmax: " + StringUtil.detailNs(list.get(list.size() - 1)));
                sb.append("\n90 th: " + StringUtil.detailNs((long) StatisticsUtil.quantileNSub(0.9, list)));
                sb.append("\n75 th: " + StringUtil.detailNs((long) StatisticsUtil.quantileNSub(0.75, list)));
                sb.append("\n50 th: " + StringUtil.detailNs((long) StatisticsUtil.quantileNSub(0.5, list)));
                sb.append("\n25 th: " + StringUtil.detailNs((long) StatisticsUtil.quantileNSub(0.25, list)));
                System.out.println(sb.toString());
            }
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
        nums.clear();
    }
}
