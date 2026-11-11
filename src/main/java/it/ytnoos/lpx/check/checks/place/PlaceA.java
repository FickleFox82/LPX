/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.place;

import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.we;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlaceA
extends PlayerCheck<WrapperPlayClientPlayerBlockPlacement> {
    private double maxDistance;
    private long teleportGrace;

    public PlaceA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.maxDistance = this.getDouble("max", 100.0);
        this.teleportGrace = this.getLong("teleport-grace", 100L);
    }

    protected CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPlayerBlockPlacement wrapperPlayClientPlayerBlockPlacement) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        long l = this.teleportGrace + (long)Math.max(0, (int)protocolPlayer.getPlayerPing());
        if (TimeUtils.difference(protocolPlayer.getLastTeleport()) < l) {
            return CheckResult.ALLOW;
        }
        Vector3i vector3i = wrapperPlayClientPlayerBlockPlacement.getBlockPosition();
        double d = vector3i.getX();
        double d2 = vector3i.getZ();
        if (d == -1.0 && d2 == -1.0) {
            return CheckResult.ALLOW;
        }
        Location location = player.getLocation();
        double d3 = Math.abs(d - location.getX()) + Math.abs(d2 - location.getZ());
        if (d3 > this.maxDistance) {
            this.F6(wrapperPlayClientPlayerBlockPlacement, new FlagInfo[]{FlagInfo.of("x", d), FlagInfo.of("z", d2), FlagInfo.of("player-x", location.getX()), FlagInfo.of("player-z", location.getZ()), FlagInfo.of("distance", (int)d3)});
            return CheckResult.DENY;
        }
        this.zl();
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

