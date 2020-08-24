package com.nineya.cspeed.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linsongwang
 * @date 2020/8/24
 */
public class StatisticsUtilTest {

    @Test
    public void quantileNAdd(){
        List<Long> list = new ArrayList<>();
        list.add(17L);
        list.add(18L);
        list.add(28L);
        list.add(29L);
        list.add(41L);
        list.add(46L);
        list.add(49L);
        list.add(57L);
        list.add(68L);
        Assert.assertEquals(17.0, StatisticsUtil.quantileNAdd(0.01, list), 0.01);
        Assert.assertEquals(17.0, StatisticsUtil.quantileNAdd(0.1, list), 0.01);
        Assert.assertEquals(23.0, StatisticsUtil.quantileNAdd(0.25, list), 0.01);
        Assert.assertEquals(53.0, StatisticsUtil.quantileNAdd(0.75, list), 0.01);
        Assert.assertEquals(68.0, StatisticsUtil.quantileNAdd(0.9, list), 0.01);
        Assert.assertEquals(68.0, StatisticsUtil.quantileNAdd(0.95, list), 0.01);
    }

    @Test
    public void quantileNSub(){
        List<Long> list = new ArrayList<>();
        list.add(17L);
        list.add(18L);
        list.add(28L);
        list.add(29L);
        list.add(41L);
        list.add(46L);
        list.add(49L);
        list.add(57L);
        list.add(68L);
        Assert.assertEquals(17.08, StatisticsUtil.quantileNSub(0.01, list), 0.01);
        Assert.assertEquals(17.8, StatisticsUtil.quantileNSub(0.1, list), 0.01);
        Assert.assertEquals(28.0, StatisticsUtil.quantileNSub(0.25, list), 0.01);
        Assert.assertEquals(41.0, StatisticsUtil.quantileNSub(0.5, list), 0.01);
        Assert.assertEquals(49.0, StatisticsUtil.quantileNSub(0.75, list), 0.01);
        Assert.assertEquals(59.2, StatisticsUtil.quantileNSub(0.9, list), 0.01);
        Assert.assertEquals(67.12, StatisticsUtil.quantileNSub(0.99, list), 0.01);
    }
}
