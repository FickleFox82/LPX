/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.monitor;

import it.ytnoos.lpx.monitor.RollingAverage;
import it.ytnoos.lpx.we;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NettyThreadAccumulator {
    private final ConcurrentLinkedQueue<BigDecimal> samples = new ConcurrentLinkedQueue();
    private final Set<UUID> players = ConcurrentHashMap.newKeySet();
    private final RollingAverage U;
    private final RollingAverage S;
    private final RollingAverage Z;

    public NettyThreadAccumulator(int n) {
        this.U = new RollingAverage(n * 10);
        this.S = new RollingAverage(n * 10);
        this.Z = new RollingAverage(n * 10);
    }

    public void addSample(long l) {
        this.samples.add(new BigDecimal(l));
    }

    public void ZV(Object object) {
        this.players.add((UUID)object);
    }

    public void Zf(Object object) {
        this.players.remove(object);
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public ConcurrentLinkedQueue getSamples() {
        return this.samples;
    }

    public void Yd(BigDecimal bigDecimal, Object object, BigDecimal bigDecimal2) {
        this.U.add(bigDecimal);
        this.S.add(bigDecimal);
        this.Z.add(bigDecimal2);
    }

    public BigDecimal VW() {
        return this.U.getPercentile(10.0);
    }

    public BigDecimal Vi() {
        return this.U.getPercentile(90.0);
    }

    public RollingAverage VP() {
        return this.U;
    }

    private static we a(we we2) {
        return we2;
    }
}

