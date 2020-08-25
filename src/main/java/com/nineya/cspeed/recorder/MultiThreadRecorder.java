package com.nineya.cspeed.recorder;

import com.nineya.cspeed.internal.SpeedEvent;
import com.nineya.cspeed.util.StatisticsUtil;
import com.nineya.cspeed.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author linsongwang
 * @date 2020/8/23
 * 用于多线程记录的记录器
 */
public class MultiThreadRecorder extends AbstractRecorder {
    private Map<String, List<Long>> nums = new ConcurrentHashMap<>();
    private Map<String, Long> startTimes = new ConcurrentHashMap<>();
    private volatile int totalCount = 0;
    // 是否输出单个线程的统计结果
    private boolean partThread = true;

    /**
     * 实例化计速器
     * @param name 记录器名称
     */
    public MultiThreadRecorder(String name) {
        super(name);
    }

    /**
     * 向记录器中添加默认的记录接口
     * @return 默认的记录接口
     */
    @Override
    protected EveryPattern setEveryPattern() {
        return (event)->{
            System.out.println(String.format("[%s]\t[%s - %s] - Count %d \t TotalCount %d - %s",
                StringUtil.getStringTime(event.getCurrentTime()),
                event.getName(),
                event.getThreadName(),
                event.getCount(),
                totalCount,
                StringUtil.nsPattern(event.getRunTime())));
        };
    }

    /**
     * 向记录器中添加默认的统计接口
     * @return 默认的统计接口
     */
    @Override
    protected StatisticPattern setStatisticPattern() {
        return new StatisticPattern<List<Long>>() {
            @Override
            public void print(String name, List<Long> list) {
                list.sort((a,b) -> (int) (a - b));
                StringBuilder sb = new StringBuilder("\n"+name + " metrics result:");
                sb.append("\ncount: " + list.size());
                sb.append("\nmean: " + StringUtil.detailNs((long) StatisticsUtil.mean(list)));
                sb.append("\nmin: " + StringUtil.detailNs(list.get(0)));
                sb.append("\nmax: " + StringUtil.detailNs(list.get(list.size() - 1)));
                sb.append("\nsum: " + StringUtil.detailNs(StatisticsUtil.sum(list)));
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
     */
    @Override
    public void onStart() {
        startTimes.put(Thread.currentThread().getName(), System.nanoTime());
    }

    /**
     * 结束一次记录，在这里进行一次记录的输出，同时将耗时的值保存到列表用于统计
     */
    @Override
    public void onEnd() {
        String threadName = Thread.currentThread().getName();
        SpeedEvent event = new SpeedEvent(name, startTimes.get(threadName));
        event.setEndTime(System.nanoTime());
        List<Long> list = nums.get(threadName);
        if (list == null){
            list = new ArrayList<>();
            nums.put(threadName, list);
        }
        list.add(event.getRunTime());
        event.setCount(list.size());
        synchronized (this){
            totalCount++;
            getEveryPattern().print(event);
        }
    }

    /**
     * 重置记录器
     */
    @Override
    public void onReset() {
        nums.clear();
        startTimes.clear();
    }

    /**
     * 停止记录器，在这里将对记录器中所有内容进行统计，统计完成后清空记录器内容
     */
    @Override
    public void onStop() {
        nums = null;
        startTimes = null;
    }

    /**
     * 统计结果
     */
    @Override
    protected void onStatistics() {
        if (partThread){
            for (Map.Entry<String, List<Long>> entry : nums.entrySet()){
                statisticPattern.print(name + " - " + entry.getKey(), entry.getValue());
            }
        }
        statisticPattern.print(name + "(Total thread)", nums.values().stream().flatMap(List::stream).collect(Collectors.toList()));
    }

    /**
     * 设置是否输出每个线程单独的统计信息
     * @param partThread true：输出，false：不输出
     * @return
     */
    public MultiThreadRecorder setPartThread(boolean partThread){
        this.partThread = partThread;
        return this;
    }

    public static MultiThreadRecorder build(String name){
        return new MultiThreadRecorder(name);
    }

    public static MultiThreadRecorder build(Class clazz){
        return new MultiThreadRecorder(clazz.getSimpleName());
    }
}
