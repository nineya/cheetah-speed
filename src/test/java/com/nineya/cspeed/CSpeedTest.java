package com.nineya.cspeed;

import com.nineya.cspeed.recorder.MultiThreadRecorder;
import com.nineya.cspeed.recorder.Recorder;
import com.nineya.cspeed.recorder.SimpleRecorder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public class CSpeedTest {

    @Test
    public void getRecorderClass(){
        Recorder recorder = CSpeed.getRecorder(this.getClass());
        for (int i = 0; i < 5; i++){
            recorder.start();
            try {
                Thread.sleep(i*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recorder.end();
        }
        recorder.stop();
        Assert.assertEquals(SimpleRecorder.class, recorder.getClass());
    }

    public void addRecorderName(){
        Recorder recorder = CSpeed.getRecorder("addRecorderName");
        for (int i = 0; i < 5; i++){
            recorder.start();
            try {
                Thread.sleep(i*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recorder.end();
        }
        recorder.stop();
        Assert.assertEquals(SimpleRecorder.class, recorder.getClass());
    }

    @Test
    public void addRecorder(){
        Recorder recorder = CSpeed.addRecorder(new SimpleRecorder("addRecorder"));
        for (int i = 0; i < 5; i++){
            recorder.start();
            try {
                Thread.sleep(i*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recorder.end();
        }
        recorder.stop();
        Assert.assertEquals(SimpleRecorder.class, recorder.getClass());
    }
}
