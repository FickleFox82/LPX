/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.flood;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class FloodA
extends PlayerCheck<PacketReceiveEvent> {
    private RollingIntervalCounter rollingIntervalCounter;

    public FloodA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.rollingIntervalCounter = new RollingIntervalCounter(this.getInt("max", 1100));
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (protocolPlayer.getPrinterData().isUsingPrinter()) {
            return CheckResult.ALLOW;
        }
        if (this.rollingIntervalCounter.increment()) {
            this.Fn(packetReceiveEvent, new FlagInfo[]{FlagInfo.of("pps", this.rollingIntervalCounter.getMaxCounter())});
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

