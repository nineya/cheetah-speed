package com.nineya.cspeed.recorder;

import com.nineya.cspeed.internal.SpeedEvent;

import java.util.List;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public abstract class AbstractRecorder implements Recorder {
    private String name;
    private EveryPattern everyPattern = setEveryPattern();
    private StatisticPattern statisticPattern = setStatisticPattern();

    public AbstractRecorder(String name) {
        this.name = name;
    }

    protected abstract EveryPattern setEveryPattern();

    protected abstract StatisticPattern setStatisticPattern();

    public String getName() {
        return name;
    }

    public Recorder setEveryPattern(EveryPattern everyPattern) {
        this.everyPattern = everyPattern;
        return this;
    }

    public Recorder setStatisticPattern(StatisticPattern statisticPattern) {
        this.statisticPattern = statisticPattern;
        return this;
    }

    public EveryPattern getEveryPattern() {
        return everyPattern;
    }

    public StatisticPattern getStatisticPattern() {
        return statisticPattern;
    }

    @FunctionalInterface
    public interface EveryPattern<T extends SpeedEvent>{
        void print(T event);
    }

    @FunctionalInterface
    public interface StatisticPattern<V>{
        void print(String name, V list);
    }
}