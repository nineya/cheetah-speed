package com.nineya.cspeed.recorder;

import com.nineya.cspeed.CSpeed;
import com.nineya.cspeed.internal.SpeedEvent;
import com.nineya.cspeed.internal.State;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public abstract class AbstractRecorder implements Recorder {
    protected String name;
    protected EveryPattern everyPattern = setEveryPattern();
    protected StatisticPattern statisticPattern = setStatisticPattern();
    protected volatile State state = State.RUN;

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
     * 开始一次记录，在这里应该创建一个SpeedEvent实体对象，传入开始时间;
     * 进行状态判断，从而知道是否能够正常的开始测试;
     * start和end希望可以多线程进行操作，所以在这里不能实现状态控制，只能由子类来实现。
     * @return
     */
    @Override
    public AbstractRecorder start() {
        if (state != State.RUN){
            System.err.println("记速器当前状态不可进行start：" + state);
            return this;
        }
        onStart();
        return this;
    }

    /**
     * 实际启动时实现记录开始时间，由子类实现
     * @return 本身
     */
    protected abstract void onStart();

    /**
     * 结束一次记录，在这里进行一次记录的输出，同时将耗时的值保存到列表用于统计
     * 进行状态判断，是否已经停止测试
     * @return 本身
     */
    @Override
    public AbstractRecorder end(){
        if (state != State.RUN){
            System.err.println("记速器当前状态不可进行end：" + state);
            return this;
        }
        onEnd();
        return this;
    }

    /**
     * 实际启动时实现记录结束时间等操作，由子类实现
     * @return 本身
     */
    protected abstract void onEnd();

    /**
     * 重置记速器,重置前执行一次统计
     * @return 执行重置记速器
     */
    @Override
    public AbstractRecorder reset() {
        if (state != State.RUN){
            System.err.println("记速器当前状态不可进行reset：" + state);
            return this;
        }
        statistics();
        state = State.SYN_ERSET;
        onReset();
        state = State.RUN;
        return this;
    }

    /**
     * 重置清空记速器内容
     */
    protected abstract void onReset();

    /**
     * 停止计时,停止前进行一次统计，执行后状态为STOP
     */
    @Override
    public void stop(){
        if (state != State.RUN){
            System.err.println("记速器当前状态不可进行stop：" + state);
            return;
        }
        statistics();
        state = State.SYN_STOP;
        onStop();
        CSpeed.remove(this);
        state = State.STOP;
    }

    /**
     * 执行停止计时器的操作
     */
    protected abstract void onStop();

    /**
     * 执行统计，执行统计时将状态设置为统计中，此时不允许进行操作记速器的行为
     * @return 统计记速器
     */
    @Override
    public AbstractRecorder statistics() {
        if (state != State.RUN){
            System.err.println("记速器当前状态不可进行statistics：" + state);
            return this;
        }
        state = State.SYN_STATISTICS;
        onStatistics();
        state = State.RUN;
        return this;
    }

    /**
     * 执行统计操作
     * @return
     */
    protected abstract void onStatistics();

    /**
     * 向记录器中添加默认的统计接口
     * @return 默认的统计接口
     */
    protected abstract StatisticPattern setStatisticPattern();

    /**
     * 取得记录器名称
     * @return 记录器名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 设置记录器的记录接口
     * @param everyPattern 记录接口
     * @return 装饰器模式，返回本身
     */
    public AbstractRecorder setEveryPattern(EveryPattern everyPattern) {
        this.everyPattern = everyPattern;
        return this;
    }

    /**
     * 设置记录器的统计接口
     * @param statisticPattern 统计接口
     * @return 装饰器模式，返回本身
     */
    public AbstractRecorder setStatisticPattern(StatisticPattern statisticPattern) {
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

    @FunctionalInterface
    public interface Collector{
        /**
         * 执行操作
         */
        void run();
    }

    public Collect collect(Collector collector){
        return new Collect(this, collector);
    }

    public class Collect{
        private int count = 1;
        private Collector collector;
        private Recorder recorder;

        public Collect(Recorder recorder, Collector collector){
            this.recorder = recorder;
            this.collector = collector;
        }

        public Collect setCount(int count){
            this.count = count;
            return this;
        }

        public Recorder run(){
            for (int i = 0; i < count; i++){
                start();
                collector.run();
                end();
            }
            return recorder;
        }
    }
}