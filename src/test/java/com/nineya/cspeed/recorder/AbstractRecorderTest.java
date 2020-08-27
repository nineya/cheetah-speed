package com.nineya.cspeed.recorder;

import com.nineya.cspeed.util.StatisticsUtil;
import com.nineya.cspeed.util.StringUtil;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void setEveryPatternTest(){
        MultiThreadRecorder recorder = MultiThreadRecorder.build("setEveryPatternTest");
        recorder.setEveryPattern((event)->{
            System.out.println("耗时" + StringUtil.detailNs(event.getRunTime()));
        }).collect(()->{
            System.out.println("执行测试！");
        }).setCount(5).run().stop();
    }

    @Test
    public void setStatisticPatternTest(){
        MultiThreadRecorder recorder = MultiThreadRecorder.build("setStatisticPatternTest");
        recorder.setStatisticPattern((name, list)->{
            ((List<Long>)list).sort((a, b) -> (int) (a - b));
            System.out.println(name + "平均耗时: " + StringUtil.detailNs((long) StatisticsUtil.mean((List<Long>) list)));
        }).collect(()->{
            System.out.println("执行测试！");
        }).setCount(5).run().stop();
    }
}
