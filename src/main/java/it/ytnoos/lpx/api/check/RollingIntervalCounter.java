/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check;

public final class RollingIntervalCounter {
    private static final int INITIAL_CAPACITY = 8;
    private static final long NANOS_PER_MILLISECOND = 1000000L;
    private final double maxCounter;
    private final long interval;
    private final long intervalNanos;
    private long[] sampleTimes;
    private double[] sampleValues;
    private int headIndex;
    private int tailIndex;
    private double counter;
    private long lastFlag;
    private boolean overLimit;

    public RollingIntervalCounter(long l) {
        this(l, 1000L);
    }

    public RollingIntervalCounter(long l, long l2) {
        if (l2 <= 0L) {
            throw new IllegalArgumentException("interval must be positive");
        }
        this.maxCounter = l;
        this.interval = l2;
        this.intervalNanos = l2 * 1000000L;
        this.sampleTimes = new long[8];
        this.sampleValues = new double[8];
    }

    public boolean increment() {
        return this.increment(1.0);
    }

    public boolean increment(double d) {
        if (this.maxCounter < 0.0) {
            return false;
        }
        long l = System.nanoTime();
        this.evictExpired(l);
        this.addSample(l, d);
        boolean bl = this.counter > this.maxCounter;
        boolean bl2 = bl && !this.overLimit;
        this.overLimit = bl;
        if (bl2) {
            this.lastFlag = System.currentTimeMillis();
        }
        return bl2;
    }

    public boolean isOverLimit() {
        if (this.maxCounter < 0.0) {
            return false;
        }
        this.evictExpired(System.nanoTime());
        this.overLimit = this.counter > this.maxCounter;
        return this.overLimit;
    }

    public long getInterval() {
        return this.interval;
    }

    public double getValue() {
        this.evictExpired(System.nanoTime());
        return this.counter;
    }

    public double getMaxCounter() {
        return this.maxCounter;
    }

    public boolean hasFlaggedRecently() {
        return this.hasFlaggedRecently(this.interval);
    }

    public boolean hasFlaggedRecently(long l) {
        return System.currentTimeMillis() - this.lastFlag < l;
    }

    private void evictExpired(long l) {
        long l2 = l - this.intervalNanos;
        while (this.headIndex != this.tailIndex && this.sampleTimes[this.headIndex] < l2) {
            this.counter -= this.sampleValues[this.headIndex];
            this.sampleValues[this.headIndex] = 0.0;
            ++this.headIndex;
            if (this.headIndex < this.sampleTimes.length) continue;
            this.headIndex = 0;
        }
        if (this.counter <= 0.0) {
            this.counter = 0.0;
        }
    }

    private void addSample(long l, double d) {
        if (d <= 0.0) {
            return;
        }
        int n = RollingIntervalCounter.incrementIndex(this.tailIndex, this.sampleTimes.length);
        if (n == this.headIndex) {
            this.resize();
            n = RollingIntervalCounter.incrementIndex(this.tailIndex, this.sampleTimes.length);
        }
        this.sampleTimes[this.tailIndex] = l;
        this.sampleValues[this.tailIndex] = d;
        this.tailIndex = n;
        this.counter += d;
    }

    private void resize() {
        int n = this.size();
        int n2 = this.sampleTimes.length * 2;
        long[] lArray = new long[n2];
        double[] dArray = new double[n2];
        for (int i = 0; i < n; ++i) {
            int n3 = (this.headIndex + i) % this.sampleTimes.length;
            lArray[i] = this.sampleTimes[n3];
            dArray[i] = this.sampleValues[n3];
        }
        this.sampleTimes = lArray;
        this.sampleValues = dArray;
        this.headIndex = 0;
        this.tailIndex = n;
    }

    private int size() {
        if (this.tailIndex >= this.headIndex) {
            return this.tailIndex - this.headIndex;
        }
        return this.tailIndex + (this.sampleTimes.length - this.headIndex);
    }

    private static int incrementIndex(int n, int n2) {
        return n + 1 >= n2 ? 0 : n + 1;
    }
}

