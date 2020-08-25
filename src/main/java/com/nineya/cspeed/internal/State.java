package com.nineya.cspeed.internal;

/**
 * @author linsongwang
 * @date 2020/8/25
 * 记速器的状态，通过状态进行控制，防止因为状态不一致出现错误。
 */
public enum  State {
    RUN, SYN_STOP, STOP, SYN_ERSET, SYN_STATISTICS;
}
