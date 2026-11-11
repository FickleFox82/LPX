/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.flood;

import com.github.retrooper.packetevents.event.simple.ReadSlotsEvent;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RepeatedIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class FloodG
extends PlayerCheck<ReadSlotsEvent> {
    private RepeatedIntervalCounter counter;
    private boolean cancel;
    private int lowSlotsThreshold;
    private double lowSlotsMultiplier;

    public FloodG(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        int n = this.getConfig().getInt(this.getPath("max"), 70);
        int n2 = this.getConfig().getInt(this.getPath("interval"), 250);
        int n3 = this.getConfig().getInt(this.getPath("periods"), 5);
        int n4 = this.getConfig().getInt(this.getPath("warnings"), 2);
        this.counter = new RepeatedIntervalCounter(n, n2, n3, n4);
        this.cancel = this.getConfig().getBoolean(this.getPath("cancel"), true);
        this.lowSlotsThreshold = Math.max(0, this.getInt("low-slots-threshold", 3));
        this.lowSlotsMultiplier = Math.max(0.1, this.getDouble("low-slots-multiplier", 0.7));
    }

    protected CheckResult check(ProtocolPlayer protocolPlayer, ReadSlotsEvent readSlotsEvent) {
        double d = (double)readSlotsEvent.getSlotsCount() * protocolPlayer.itemFloodMultiplier();
        if (readSlotsEvent.getSlotsCount() <= this.lowSlotsThreshold) {
            d *= this.lowSlotsMultiplier;
        }
        if (this.counter.increment(d)) {
            this.Fn(readSlotsEvent, new FlagInfo[]{FlagInfo.of("max-counter", this.counter.getMaxCounter()), FlagInfo.of("interval", this.counter.getInterval() + "ms"), FlagInfo.of("slots-count", readSlotsEvent.getSlotsCount()), FlagInfo.of("amount", d)});
            return this.cancel ? CheckResult.DENY : CheckResult.ALLOW;
        }
        if (this.cancel && this.counter.isOverLimit()) {
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

