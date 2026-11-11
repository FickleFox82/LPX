/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package it.ytnoos.lpx.check.checks.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FloodF
extends PlayerCheck<PlayerCommandPreprocessEvent> {
    private RollingIntervalCounter rollingIntervalCounter;

    public FloodF(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.rollingIntervalCounter = new RollingIntervalCounter(this.getInt("max", 8));
    }

    protected CheckResult o2(Object object, PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        if (this.rollingIntervalCounter.increment()) {
            this.Fn(playerCommandPreprocessEvent, new FlagInfo[]{FlagInfo.of("command", playerCommandPreprocessEvent.getMessage())});
            return CheckResult.DENY;
        }
        if (this.rollingIntervalCounter.isOverLimit()) {
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

