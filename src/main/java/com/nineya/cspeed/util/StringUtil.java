package com.nineya.cspeed.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author linsongwang
 * @date 2020/8/23
 */
public class StringUtil {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String getStringTime(long time){
        return sdf.format(time);
    }

    public static String decimals(int place, double num){
        return String.format("%."+place+"f", num);
    }

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
}
