package com.nineya.cspeed;

import com.nineya.cspeed.recorder.AbstractRecorder;
import com.nineya.cspeed.recorder.Recorder;
import com.nineya.cspeed.recorder.SimpleRecorder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsongwang
 * @date 2020/8/23
 * 用于管理记录器的类
 */
public class CSpeed {
    private static Map<String, AbstractRecorder> recorders = new ConcurrentHashMap<String, AbstractRecorder>();

    /**
     * 传入Class，通过class取得类名作为记录器名称，调用getRecorder(String)进行getRecorder操作
     * @param clazz 类
     * @return Recorder记录器
     */
    public static AbstractRecorder getRecorder(Class clazz){
        return getRecorder(clazz.getSimpleName());
    }

    /**
     * 传入记录器名称，如果记录器已经存在，则直接get
     * @param name 记录器名称
     * @return Recorder记录器
     */
    public static AbstractRecorder getRecorder(String name){
        return recorders.get(name);
    }

    /**
     * 如果指定名称的记录器不存在，则使用指定的记录器创建。
     * @param name 记录器名称
     * @param recorder Recorder记录器对象
     * @return Recorder记录器
     */
    public static AbstractRecorder getRecorder(String name, AbstractRecorder recorder){
        if (recorders.containsKey(name)){
            return recorders.get(name);
        }
        return addRecorder(recorder);
    }

    /**
     * 往列表中添加一个记录器
     * @param recorder 记录器
     * @return Recorder记录器
     */
    public static<T extends AbstractRecorder> T addRecorder(T recorder){
        recorders.put(recorder.getName(), recorder);
        return recorder;
    }
}
