/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.EventLoop
 */
package it.ytnoos.lpx.monitor;

import io.netty.channel.EventLoop;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.monitor.NettyThreadAccumulator;
import it.ytnoos.lpx.monitor.RollingAverage;
import it.ytnoos.lpx.monitor.ThreadUtil;
import it.ytnoos.lpx.monitor.tick.BukkitTickHook;
import it.ytnoos.lpx.monitor.tick.PaperTickHook;
import it.ytnoos.lpx.monitor.tick.TickHook;
import it.ytnoos.lpx.we;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PacketTickMonitor {
    private static final int TPS = 20;
    private boolean running = false;
    private final RollingAverage Q = new RollingAverage(200);
    private final RollingAverage k = new RollingAverage(1200);
    private final RollingAverage K = new RollingAverage(6000);
    private final RollingAverage A = new RollingAverage(200);
    private final RollingAverage v = new RollingAverage(1200);
    private final RollingAverage s = new RollingAverage(200);
    private final RollingAverage V = new RollingAverage(1200);
    private final Map<EventLoop, Integer> n = new ConcurrentHashMap<EventLoop, Integer>();
    private final Map<EventLoop, NettyThreadAccumulator> D = new ConcurrentHashMap<EventLoop, NettyThreadAccumulator>();
    private final LPXBukkit lpxBukkit;
    private final TickHook tickHook;
    private static final String PAPER_TICK_EVENT = "com.destroystokyo.paper.event.server.ServerTickStartEvent";

    public PacketTickMonitor(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        this.tickHook = this.pt();
        this.tickHook.j9(n -> this.pi());
    }

    public void pc() {
        if (this.running) {
            return;
        }
        this.running = true;
        this.tickHook.oo();
    }

    public void pj() {
        if (!this.running) {
            return;
        }
        this.tickHook.close();
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    private NettyThreadAccumulator getOrCreateAccumulator(Thread thread, EventLoop eventLoop2) {
        this.n.computeIfAbsent(eventLoop2, eventLoop -> ThreadUtil.extractNumber(thread.getName()));
        return this.D.computeIfAbsent(eventLoop2, eventLoop -> new NettyThreadAccumulator(20));
    }

    public void recordDelay(long l, EventLoop eventLoop) {
        if (this.running) {
            this.getOrCreateAccumulator(Thread.currentThread(), eventLoop).addSample(l);
        }
    }

    public void hM(UUID uUID, Thread thread, EventLoop eventLoop) {
        this.getOrCreateAccumulator(thread, eventLoop).ZV(uUID);
    }

    public void ht(UUID uUID, Thread thread, EventLoop eventLoop) {
        this.getOrCreateAccumulator(thread, eventLoop).Zf(uUID);
    }

    public void pi() {
        Object object;
        Serializable serializable;
        ArrayList<BigDecimal> arrayList = new ArrayList<BigDecimal>();
        for (NettyThreadAccumulator object2 : this.D.values()) {
            serializable = new ArrayList<BigDecimal>();
            while ((object = (BigDecimal)object2.getSamples().poll()) != null) {
                serializable.add((BigDecimal)object);
                arrayList.add((BigDecimal)object);
            }
            if (serializable.isEmpty()) {
                object2.Yd(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
                continue;
            }
            Object[] objectArray = serializable.toArray(new BigDecimal[0]);
            Arrays.sort(objectArray);
            BigDecimal bigDecimal = this.calculateAverage((BigDecimal[])objectArray);
            BigDecimal bigDecimal2 = this.getPercentile((BigDecimal[])objectArray, 95.0);
            Object object3 = objectArray[objectArray.length - 1];
            object2.Yd(bigDecimal, bigDecimal2, (BigDecimal)object3);
        }
        if (arrayList.isEmpty()) {
            this.record(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            Object[] objectArray = arrayList.toArray(new BigDecimal[0]);
            Arrays.sort(objectArray);
            BigDecimal bigDecimal = this.calculateAverage((BigDecimal[])objectArray);
            serializable = this.getPercentile((BigDecimal[])objectArray, 95.0);
            object = objectArray[objectArray.length - 1];
            this.record(bigDecimal, (BigDecimal)serializable, (BigDecimal)object);
        }
    }

    private void record(BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3) {
        this.Q.add(bigDecimal);
        this.k.add(bigDecimal);
        this.K.add(bigDecimal);
        this.A.add(bigDecimal2);
        this.v.add(bigDecimal2);
        this.s.add(bigDecimal3);
        this.V.add(bigDecimal3);
    }

    private BigDecimal calculateAverage(BigDecimal[] bigDecimalArray) {
        BigDecimal[] bigDecimalArray2 = bigDecimalArray;
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (BigDecimal bigDecimal2 : bigDecimalArray2) {
            bigDecimal = bigDecimal.add(bigDecimal2);
        }
        return bigDecimal.divide(new BigDecimal(bigDecimalArray2.length), 30, RoundingMode.HALF_UP);
    }

    private BigDecimal getPercentile(BigDecimal[] bigDecimalArray, double d) {
        BigDecimal[] bigDecimalArray2 = bigDecimalArray;
        int n = (int)Math.ceil(d / 100.0 * (double)bigDecimalArray2.length);
        return bigDecimalArray2[Math.max(0, Math.min(n - 1, bigDecimalArray2.length - 1))];
    }

    public BigDecimal pa() {
        return this.Q.Ku();
    }

    public BigDecimal p5() {
        return this.k.Ku();
    }

    public BigDecimal ps() {
        return this.K.Ku();
    }

    public BigDecimal pA() {
        return this.Q.getPercentile(50.0);
    }

    public BigDecimal pQ() {
        return this.k.getPercentile(50.0);
    }

    public BigDecimal p3() {
        return this.K.getPercentile(50.0);
    }

    public BigDecimal pW() {
        return this.Q.getPercentile(10.0);
    }

    public BigDecimal px() {
        return this.Q.getPercentile(90.0);
    }

    public BigDecimal pK() {
        return this.A.getPercentile(95.0);
    }

    public BigDecimal p2() {
        return this.v.getPercentile(95.0);
    }

    public BigDecimal pJ() {
        return this.K.getPercentile(95.0);
    }

    public BigDecimal pD() {
        return this.s.KG();
    }

    public BigDecimal pZ() {
        return this.V.KG();
    }

    public BigDecimal ph() {
        return this.K.KG();
    }

    public int getCurrentTick() {
        return this.tickHook.getCurrentTick();
    }

    public Map getThreadPlayers() {
        HashMap<Integer, Sample> hashMap = new HashMap<Integer, Sample>();
        for (Map.Entry<EventLoop, Integer> entry : this.n.entrySet()) {
            EventLoop eventLoop = entry.getKey();
            Integer n = entry.getValue();
            NettyThreadAccumulator nettyThreadAccumulator = this.D.get(eventLoop);
            if (nettyThreadAccumulator == null) continue;
            hashMap.put(n, new Sample(nettyThreadAccumulator.Vi(), nettyThreadAccumulator.getPlayerCount()));
        }
        return hashMap;
    }

    private TickHook pt() {
        if (PacketTickMonitor.classExists(PAPER_TICK_EVENT)) {
            return new PaperTickHook(this.lpxBukkit.getPlugin());
        }
        return new BukkitTickHook(this.lpxBukkit.getPlugin());
    }

    private static boolean classExists(String string) {
        try {
            Class.forName(string);
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    private static we a(we we2) {
        return we2;
    }

    public static class Sample {
        private final BigDecimal bigDecimal;
        private final int playerCount;

        public Sample(BigDecimal bigDecimal, int n) {
            this.bigDecimal = bigDecimal;
            this.playerCount = n;
        }

        public BigDecimal getValue() {
            return this.bigDecimal;
        }

        public int getPlayerCount() {
            return this.playerCount;
        }
    }
}

