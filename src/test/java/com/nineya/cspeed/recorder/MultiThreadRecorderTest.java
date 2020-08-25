package com.nineya.cspeed.recorder;

import com.nineya.cspeed.CSpeed;
import com.nineya.cspeed.recorder.MultiThreadRecorder;
import com.nineya.cspeed.recorder.Recorder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/25
 */
public class MultiThreadRecorderTest {
    @Test
    public void MultiThreadTest(){
        Recorder re = CSpeed.addRecorder(MultiThreadRecorder.build("MultiThreadTest"));
        for (int i = 0; i < 10; i++){
            new Thread(()->{
                Recorder recorder = CSpeed.getRecorder("MultiThreadTest");
                for (int j = 0; j < 20; j++){
                    recorder.start();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    recorder.end();
                }
            }).start();
        }
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        re.stop();
        Assert.assertEquals("MultiThreadTest", re.getName());
        Assert.assertEquals(MultiThreadRecorder.class, re.getClass());
    }
}
