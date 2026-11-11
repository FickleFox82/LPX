/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;

public class mm
extends PlayerCheck<PacketLimitException> {
    private final boolean X;

    public mm(ProtocolPlayer protocolPlayer, CheckOptions checkOptions, boolean bl) {
        super(protocolPlayer, checkOptions);
        this.X = bl;
    }

    public CheckResult r5(Object object, PacketLimitException packetLimitException) {
        this.Fn(packetLimitException, new FlagInfo[]{FlagInfo.of("limit", packetLimitException.getLimit().getDescription()), FlagInfo.of("value", packetLimitException.getValue()), FlagInfo.of("max", packetLimitException.getMax())});
        return CheckResult.DENY;
    }

    @Override
    public boolean isPreVia() {
        return this.X;
    }
}

