package com.nineya.cspeed.util;

import java.util.List;

/**
 * @author linsongwang
 * @date 2020/8/24
 */
public class StatisticsUtil {
    /**
     * 计算分位值，N+1法
     * @param n 分位数，大于0，小于1
     * @param list 待求分位值的列表，需要先排序好
     * @return 分位值
     */
    public static double quantileNAdd(double n, List<Long> list){
        if (n <= 0 || n>=1){
            throw new RuntimeException("分位值 n 错误：" + n);
        }
        double position = (list.size() + 1)*n;
        int m = (int)position - 1;
        if (m >= list.size() - 1){
            return list.get(list.size() - 1);
        }
        if (m < 0){
            return list.get(0);
        }
        long a = list.get(m);
        m++;
        return a + (position - m) * (list.get(m) - a);
    }

    /**
     * 计算分位值，N-1法
     * @param n 分位数，大于0，小于1
     * @param list 待求分位值的列表，需要先排序好
     * @return 分位值
     */
    public static double quantileNSub(double n, List<Long> list){
        if (n <= 0 || n>=1){
            throw new RuntimeException("分位值 n 错误：" + n);
        }
        double position = (list.size() - 1) * n + 1;
        int m = (int)position;
        if (m <= 0){
            return list.get(0);
        }
        long a = list.get(m);
        long b = list.get(m-1);
        return b + (position - m) * (a - b);
    }

    /**
     * 计算平均值
     * @param list 需要计算平均值的列表
     * @return 平均值
     */
    public static double mean(List<Long> list){
        return list.stream().mapToDouble(a->(double)a).average().getAsDouble();
    }
}
