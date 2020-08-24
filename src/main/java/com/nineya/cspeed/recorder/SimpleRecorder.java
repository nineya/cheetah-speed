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

    /**
     * 创建默认的记录接口实现
     * @return 记录接口
     */
    @Override
    protected EveryPattern setEveryPattern() {
        return (event)->{
            System.out.println(String.format("[%s]\t[%s - %s] - 第%s次 - %s",
                    StringUtil.getStringTime(event.getCurrentTime()),
                    event.getName(),
                    event.getThreadName(),
                    event.getCount(),
                    StringUtil.nsPattern(event.getRunTime())));
        };
    }

    /**
     * 创建默认的统计接口实现
     * @return 统计接口
     */
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

    /**
     * 开始一次记录，在这里应该创建一个SpeedEvent实体对象，传入开始时间
     * @return 返回本身，装饰器模式
     */
    @Override
    public SimpleRecorder start() {
        event = new SpeedEvent(getName(), System.nanoTime());
        return this;
    }

    /**
     * 结束一次记录，在这里进行一次记录的输出，同时将耗时的值保存到列表用于统计
     * @return 返回本身，装饰器模式
     */
    @Override
    public SimpleRecorder end() {
        event.setEndTime(System.nanoTime());
        nums.add(event.getRunTime());
        event.setCount(nums.size());
        getEveryPattern().print(event);
        return this;
    }

    /**
     * 停止记录器，在这里将对记录器中所有内容进行统计，统计完成后清空记录器内容
     */
    @Override
    public void stop() {
        getStatisticPattern().print(getName(), nums);
        nums.clear();
    }
}
