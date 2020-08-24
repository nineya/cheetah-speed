package com.nineya.cspeed.recorder;

import com.nineya.cspeed.internal.SpeedEvent;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public abstract class AbstractRecorder implements Recorder {
    private String name;
    private EveryPattern everyPattern = setEveryPattern();
    private StatisticPattern statisticPattern = setStatisticPattern();

    /**
     * 构造方法
     * @param name 记录器名称
     */
    public AbstractRecorder(String name) {
        this.name = name;
    }

    /**
     * 向记录器中添加默认的记录接口
     * @return 默认的记录接口
     */
    protected abstract EveryPattern setEveryPattern();

    /**
     * 向记录器中添加默认的统计接口
     * @return 默认的统计接口
     */
    protected abstract StatisticPattern setStatisticPattern();

    /**
     * 取得记录器名称
     * @return 记录器名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置记录器的记录接口
     * @param everyPattern 记录接口
     * @return 装饰器模式，返回本身
     */
    public Recorder setEveryPattern(EveryPattern everyPattern) {
        this.everyPattern = everyPattern;
        return this;
    }

    /**
     * 设置记录器的统计接口
     * @param statisticPattern 统计接口
     * @return 装饰器模式，返回本身
     */
    public Recorder setStatisticPattern(StatisticPattern statisticPattern) {
        this.statisticPattern = statisticPattern;
        return this;
    }

    /**
     * 取得记录接口的实现
     * @return 记录接口的实现类
     */
    public EveryPattern getEveryPattern() {
        return everyPattern;
    }

    /**
     * 返回统计接口的实现
     * @return 统计接口的实现类
     */
    public StatisticPattern getStatisticPattern() {
        return statisticPattern;
    }

    /**
     * 记录器接口，添加函数式编程
     * @param <T> SpeedEvent消息实体
     */
    @FunctionalInterface
    public interface EveryPattern<T extends SpeedEvent>{
        /**
         * 根据SpeedEvent消息实体打印信息
         * @param event SpeedEvent消息实体
         */
        void print(T event);
    }

    /**
     * 统计接口，添加了函数式编程支持
     * @param <V> 泛型，一般是执行的结果列表，用于统计测试结果
     */
    @FunctionalInterface
    public interface StatisticPattern<V>{
        /**
         * 打印统计结果
         * @param name 记录器名称
         * @param list 泛型，一般是执行的结果列表，用于统计测试结果
         */
        void print(String name, V list);
    }
}