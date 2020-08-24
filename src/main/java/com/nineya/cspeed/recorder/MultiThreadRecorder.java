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
        return null;
    }

    /**
     * 向记录器中添加默认的统计接口
     * @return 默认的统计接口
     */
    @Override
    protected StatisticPattern setStatisticPattern() {
        return null;
    }


    /**
     * 开始一次记录，在这里应该创建一个SpeedEvent实体对象，传入开始时间
     * @return 返回本身，装饰器模式
     */
    @Override
    public Recorder start() {
        return null;
    }

    /**
     * 结束一次记录，在这里进行一次记录的输出，同时将耗时的值保存到列表用于统计
     * @return 返回本身，装饰器模式
     */
    @Override
    public Recorder end() {
        return null;
    }

    /**
     * 停止记录器，在这里将对记录器中所有内容进行统计，统计完成后清空记录器内容
     */
    @Override
    public void stop() {

    }
}
