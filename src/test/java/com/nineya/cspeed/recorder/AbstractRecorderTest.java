package com.nineya.cspeed.recorder;

import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/25
 */
public class AbstractRecorderTest {
    @Test
    public void collectTest(){
        MultiThreadRecorder recorder = MultiThreadRecorder.build("collectTest");
        recorder.collect(()->{
            System.out.println("执行测试！");
        })
        .setCount(5)
        .run()
        .stop();
    }
}
