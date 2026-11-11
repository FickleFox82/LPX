/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.position;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class PositionD
extends PlayerCheck<WrapperPlayClientPlayerFlying> {
    private int max;

    public PositionD(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getConfig().getInt(this.getPath("max"), 1000000);
    }

    public CheckResult l3(Object object, WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        if (!wrapperPlayClientPlayerFlying.hasRotationChanged()) {
            return CheckResult.ALLOW;
        }
        float f2 = wrapperPlayClientPlayerFlying.getLocation().getYaw();
        if (Math.abs(f2) > (float)this.max) {
            this.Fn(wrapperPlayClientPlayerFlying, new FlagInfo[]{FlagInfo.of("yaw", Float.valueOf(f2))});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

