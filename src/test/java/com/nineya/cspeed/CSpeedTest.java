package com.nineya.cspeed;

import com.nineya.cspeed.recorder.Recorder;
import com.nineya.cspeed.recorder.SimpleRecorder;
import org.junit.Test;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public class CSpeedTest {
    @Test
    public void addRecorder(){
        Recorder recorder = CSpeed.addRecorder(new SimpleRecorder(this.getClass().getSimpleName()));
        for (int i = 0; i < 10; i++){
            recorder.start();
            try {
                Thread.sleep(i*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recorder.end();
        }
        recorder.stop();
    }
}
