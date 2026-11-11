/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.payload;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.BukkitCheckManager;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.we;

public class PayloadB
extends PlayerCheck<WrapperPlayClientPluginMessage> {
    private long last;
    private int delay;
    private RollingIntervalCounter rollingIntervalCounter;

    public PayloadB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.delay = this.getInt("delay", 1000);
        this.rollingIntervalCounter = new RollingIntervalCounter(this.getInt("max", 15));
    }

    public CheckResult cu(Object object, WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        String string = wrapperPlayClientPluginMessage.getChannelName();
        if (string.equals("BungeeCord")) {
            return CheckResult.ALLOW;
        }
        if (!BukkitCheckManager.BOOK_CHANNELS.contains(string)) {
            return CheckResult.ALLOW;
        }
        if (this.rollingIntervalCounter.increment()) {
            this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[]{FlagInfo.of("pps", this.rollingIntervalCounter.getMaxCounter())});
            return CheckResult.DENY;
        }
        if (this.rollingIntervalCounter.isOverLimit()) {
            return CheckResult.DENY;
        }
        long l = TimeUtils.difference(this.last);
        this.last = TimeUtils.current();
        if (l < (long)this.delay) {
            this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[]{FlagInfo.of("delay", l)});
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

