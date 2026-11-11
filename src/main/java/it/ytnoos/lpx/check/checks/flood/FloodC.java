/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.flood;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RepeatedIntervalCounter;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.utils.ItemUtils;
import it.ytnoos.lpx.we;

public class FloodC
extends PlayerCheck<ItemStackDecodeEvent> {
    private RepeatedIntervalCounter repeatedIntervalCounter;
    private RollingIntervalCounter sustained;
    private long bigThreshold;
    private long harmlessThreshold;
    private double harmlessMultiplier;

    public FloodC(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        int n = this.getInt("reset-interval", 100);
        int n2 = this.getInt("max-periods", 6);
        int n3 = this.getInt("max-warnings", 3);
        int n4 = this.getInt("sustained-max", 32);
        int n5 = this.getInt("sustained-interval", 1000);
        this.bigThreshold = this.getLong("big-threshold", 6000L);
        this.harmlessThreshold = this.getLong("harmless-threshold", 512L);
        this.harmlessMultiplier = Math.max(0.01, this.getDouble("harmless-multiplier", 0.05));
        this.repeatedIntervalCounter = new RepeatedIntervalCounter(this.getInt("max", 20), n, n2, n3);
        this.sustained = n4 >= 0 ? new RollingIntervalCounter(n4, n5) : null;
    }

    protected CheckResult check(ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (protocolPlayer.getPrinterData().isUsingPrinter()) {
            return CheckResult.ALLOW;
        }
        int n = itemStackDecodeEvent.getItemStack().getDecodedSizeBytes();
        if (n <= 0) {
            return CheckResult.ALLOW;
        }
        double d = (int)((long)n / this.bigThreshold / 4L);
        if (d == 0.0) {
            d = 1.0;
        }
        if ((long)n < this.bigThreshold) {
            d = 0.25;
        }
        if ((long)n <= this.harmlessThreshold) {
            d *= this.harmlessMultiplier;
        }
        d = ItemUtils.isEmpty(itemStackDecodeEvent.getItemStack()) ? (d *= 0.15) : (d *= protocolPlayer.itemFloodMultiplier());
        if (this.repeatedIntervalCounter.increment(d)) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("max-counter", this.repeatedIntervalCounter.getMaxCounter()), FlagInfo.of("size", n)});
            return CheckResult.DENY;
        }
        if (this.sustained != null) {
            if (this.sustained.increment(d)) {
                this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("mode", "rolling"), FlagInfo.of("max-counter", this.sustained.getMaxCounter()), FlagInfo.of("interval", this.sustained.getInterval() + "ms"), FlagInfo.of("size", n)});
                return CheckResult.DENY;
            }
            if (this.sustained.isOverLimit()) {
                return CheckResult.DENY;
            }
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

