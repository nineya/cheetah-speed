package com.nineya.cspeed.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public class StringUtil {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 将时间戳转为时间字符串
     * @param time 时间戳
     * @return 时间字符串
     */
    public static String getStringTime(long time){
        return sdf.format(time);
    }

    /**
     * 保留指定位数的double小数点
     * @param place 小数点位数
     * @param num double数值
     * @return String类型的值
     */
    public static String decimals(int place, double num){
        return String.format("%."+place+"f", num);
    }

    /**
     * 将纳秒（ns）时间转换为带单位的格式
     * @param time 纳秒时间戳
     * @return 时间字符串
     */
    public static String nsPattern(long time){
        StringBuilder sb = new StringBuilder();
        sb.insert(0, time%1000 + "ns");
        time /= 1000;
        if (time > 0){
            sb.insert(0, time%60 + "us ");
        }
        time /= 1000;
        if (time > 0){
            sb.insert(0, msPattern(time) + " ");
        }
        return sb.toString();
    }

    /**
     * 将毫秒（ms）时间转换为带单位的字符串时间
     * @param time ms时间戳
     * @return 时间字符串
     */
    public static String msPattern(long time){
        StringBuilder sb = new StringBuilder();
        sb.insert(0, time%1000 + "ms");
        time /= 1000;
        if (time > 0){
            sb.insert(0, time%60 + "s ");
        }
        time /= 60;
        if (time > 0){
            sb.insert(0, time%60 + "m ");
        }
        time /= 60;
        if (time > 0){
            sb.insert(0, time + "h ");
        }
        return sb.toString();
    }

    public static double nsToMs(long time){
        return time/1000000;
    }

    public static String detailNs(long time){
        return String.format("%s (%.2f ms)", nsPattern(time), (double)time/1000000);
    }
}
