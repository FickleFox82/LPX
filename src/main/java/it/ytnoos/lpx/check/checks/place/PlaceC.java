/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.place;

import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlaceC
extends PlayerCheck<WrapperPlayClientPlayerDigging> {
    private double max;

    public PlaceC(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getDouble("max", 50.0);
    }

    protected CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPlayerDigging wrapperPlayClientPlayerDigging) {
        double d;
        double d2;
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (wrapperPlayClientPlayerDigging.getAction().ordinal() > 2) {
            return CheckResult.ALLOW;
        }
        Location location = player.getLocation();
        Vector3i vector3i = wrapperPlayClientPlayerDigging.getBlockPosition();
        double d3 = location.getX() - ((double)vector3i.getX() + 0.5);
        double d4 = d3 * d3 + (d2 = location.getY() - ((double)vector3i.getY() + 0.5) + 1.5) * d2 + (d = location.getZ() - ((double)vector3i.getZ() + 0.5)) * d;
        if (d4 > this.max) {
            this.F6(wrapperPlayClientPlayerDigging, new FlagInfo[]{FlagInfo.of("distanceX", d3), FlagInfo.of("distanceY", d2), FlagInfo.of("distanceZ", d), FlagInfo.of("distance", (int)d4)});
            return CheckResult.DENY;
        }
        this.zl();
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

