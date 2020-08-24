package com.nineya.cspeed.util;

import java.util.List;

/**
 * @author linsongwang
 * @date 2020/8/24
 */
public class StatisticsUtil {
    /**
     * 计算分位值
     * @param n
     * @param list
     * @return
     */
    public static double quantile(int n, List<Long> list){
        list.sort((a,b)-> Math.toIntExact(a - b));
        return 0;
    }
}
