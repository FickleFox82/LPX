/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check;

import it.ytnoos.lpx.we;

public class IntervalCounter {
    protected final long maxCounter;
    protected final long interval;
    protected double counter;
    protected long startTime = System.currentTimeMillis();
    protected long lastFlag = 0L;

    public IntervalCounter(long l) {
        this.maxCounter = l;
        this.interval = 1000L;
    }

    public IntervalCounter(long l, long l2) {
        this.maxCounter = l;
        this.interval = l2;
    }

    public boolean increment() {
        return this.increment(1.0);
    }

    public boolean increment(double d) {
        if (this.maxCounter < 0L) {
            return false;
        }
        if (System.currentTimeMillis() - this.startTime >= this.interval) {
            this.reset();
        }
        this.counter += d;
        if (this.counter > (double)this.maxCounter) {
            this.lastFlag = System.currentTimeMillis();
            this.reset();
            return true;
        }
        return false;
    }

    protected void reset() {
        this.counter = 0.0;
        this.startTime = System.currentTimeMillis();
    }

    public long getInterval() {
        return this.interval;
    }

    public double getValue() {
        return this.counter;
    }

    public long getMaxCounter() {
        return this.maxCounter;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean hasFlaggedRecently() {
        return this.hasFlaggedRecently(this.interval);
    }

    public boolean hasFlaggedRecently(long l) {
        return System.currentTimeMillis() - this.lastFlag < l;
    }

    private static we a(we we2) {
        return we2;
    }
}

