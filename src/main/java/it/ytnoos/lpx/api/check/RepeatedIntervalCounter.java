/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check;

import it.ytnoos.lpx.api.check.IntervalCounter;
import it.ytnoos.lpx.we;
import java.util.Arrays;

public class RepeatedIntervalCounter
extends IntervalCounter {
    private final int maxWarnings;
    private final boolean[] periodWarnings;
    private int period;

    public RepeatedIntervalCounter(int n, int n2, int n3, int n4) {
        super(n, n2);
        this.maxWarnings = n4;
        this.periodWarnings = new boolean[n3];
    }

    public RepeatedIntervalCounter(int n, int n2, int n3) {
        this(n, 1000, n2, n3);
    }

    @Override
    public boolean increment(double d) {
        if (this.maxCounter < 0L) {
            return false;
        }
        boolean bl = this.advancePeriodsIfNeeded(System.currentTimeMillis());
        this.counter += d;
        return bl;
    }

    public boolean isOverLimit() {
        if (this.maxCounter < 0L) {
            return false;
        }
        this.advancePeriodsIfNeeded(System.currentTimeMillis());
        return this.counter > (double)this.maxCounter;
    }

    @Override
    public double getValue() {
        this.advancePeriodsIfNeeded(System.currentTimeMillis());
        return this.counter;
    }

    private boolean advancePeriods(int n) {
        if (n >= this.periodWarnings.length) {
            this.resetWarnings();
            this.period = 0;
            this.counter = 0.0;
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.counter > (double)this.maxCounter) {
                this.periodWarnings[this.period] = true;
            }
            ++this.period;
            if (this.period > this.periodWarnings.length - 1) {
                this.period = 0;
            }
            this.periodWarnings[this.period] = false;
            this.counter = 0.0;
        }
        if (this.countWarnings() >= this.maxWarnings) {
            this.resetWarnings();
            return true;
        }
        return false;
    }

    public int getPeriod() {
        return this.period;
    }

    public int getWarnings() {
        this.advancePeriodsIfNeeded(System.currentTimeMillis());
        return this.countWarnings();
    }

    public int getMaxPeriods() {
        return this.periodWarnings.length;
    }

    public int getMaxWarnings() {
        return this.maxWarnings;
    }

    public void resetWarnings() {
        Arrays.fill(this.periodWarnings, false);
    }

    private boolean advancePeriodsIfNeeded(long l) {
        long l2 = l - this.startTime;
        if (l2 < this.interval) {
            return false;
        }
        int n = (int)(l2 / this.interval);
        boolean bl = this.advancePeriods(n);
        this.startTime += (long)n * this.interval;
        return bl;
    }

    private int countWarnings() {
        int n = 0;
        for (boolean bl : this.periodWarnings) {
            if (!bl) continue;
            ++n;
        }
        return n;
    }

    private static we b(we we2) {
        return we2;
    }
}

