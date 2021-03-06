package com.nineya.cspeed.recorder;

/**
 * @author linsongwang
 * @date 2020/8/24
 * 计速器的抽象接口，定义了计速器的基础操作
 */
public interface Recorder {

    /**
     * 取得记速器名称
     * @return 记速器名称
     */
    String getName();

    /**
     * 开始一次记录，在这里应该创建一个SpeedEvent实体对象，传入开始时间
     * @return 返回本身，装饰器模式
     */
    Recorder start();

    /**
     * 结束一次记录，在这里进行一次记录的输出，同时将耗时的值保存到列表用于统计
     * @return 返回本身，装饰器模式
     */
    Recorder end();

    /**
     * 执行统计
     * @return 返回本身，装饰器模式
     */
    Recorder statistics();

    /**
     * 重置计速器
     */
    Recorder reset();

    /**
     * 停止记录器，在这里将对记录器中所有内容进行统计，统计完成后清空记录器内容
     */
    void stop();
}
