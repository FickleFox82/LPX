/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.position;

import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.PrinterStatus;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.player.BukkitProtocolPlayer;
import it.ytnoos.lpx.we;

public class PositionA
extends PlayerCheck<WrapperPlayClientPlayerFlying> {
    private static final double O = 3.0E7;
    private static final double i = 2.0E7;

    public PositionA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        if (!wrapperPlayClientPlayerFlying.hasPositionChanged()) {
            return CheckResult.ALLOW;
        }
        Location location = wrapperPlayClientPlayerFlying.getLocation();
        double d = location.getX();
        double d2 = location.getY();
        double d3 = location.getZ();
        if (!(this.isValid(d, 3.0E7) && this.isValid(d2, 2.0E7) && this.isValid(d3, 3.0E7))) {
            this.Fn(wrapperPlayClientPlayerFlying, new FlagInfo[]{FlagInfo.of("x", d), FlagInfo.of("y", d2), FlagInfo.of("z", d3)});
            return CheckResult.DENY;
        }
        if (protocolPlayer.getPrinterData().getPrinterStatus() == PrinterStatus.AUTOMATIC && TimeUtils.difference(protocolPlayer.getPrinterData().getLastPlace()) > (long)((BukkitProtocolPlayer)protocolPlayer).getLpxBukkit().getConfigManager().getOptionsConfig().getPrinterConfig().getDisableDelay()) {
            protocolPlayer.getPrinterData().setPrinterMode(false);
        }
        return CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private boolean isValid(double d, double d2) {
        return !Double.isNaN(d) && !Double.isInfinite(d) && !(Math.abs(d) > d2);
    }

    private static we a(we we2) {
        return we2;
    }
}

