/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.common.utils;

public class TimeUtils {
    private TimeUtils() {
    }

    public static long difference(long l) {
        return TimeUtils.current() - l;
    }

    public static long current() {
        return System.currentTimeMillis();
    }
}

