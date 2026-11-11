/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.book;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.we;

public class BookB
extends PlayerCheck<WrapperPlayClientEditBook> {
    private long last;
    private int delay;
    private RollingIntervalCounter rollingIntervalCounter;

    public BookB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.delay = this.getInt("delay", 1000);
        this.rollingIntervalCounter = new RollingIntervalCounter(this.getInt("max", 15));
    }

    protected CheckResult ug(Object object, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        if (this.rollingIntervalCounter.increment()) {
            this.Fn(wrapperPlayClientEditBook, new FlagInfo[]{FlagInfo.of("pps", this.rollingIntervalCounter.getMaxCounter())});
            return CheckResult.DENY;
        }
        if (this.rollingIntervalCounter.isOverLimit()) {
            return CheckResult.DENY;
        }
        long l = TimeUtils.difference(this.last);
        this.last = TimeUtils.current();
        if (l < (long)this.delay) {
            this.Fn(wrapperPlayClientEditBook, new FlagInfo[]{FlagInfo.of("delay", l)});
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

