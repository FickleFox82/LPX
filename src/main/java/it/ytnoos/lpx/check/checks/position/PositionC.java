/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 */
package it.ytnoos.lpx.check.checks.position;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Entity;

public class PositionC
extends PlayerCheck<WrapperPlayClientPlayerFlying> {
    private int max;
    private long lastTeleport;
    private static final String MAX_PATH = "max";

    public PositionC(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getConfig().getInt(this.getPath(MAX_PATH), 300000);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        if (wrapperPlayClientPlayerFlying.hasPositionChanged() && Math.abs(wrapperPlayClientPlayerFlying.getLocation().getY()) > (double)this.max) {
            this.teleportBelow(protocolPlayer);
            this.Fn(wrapperPlayClientPlayerFlying, new FlagInfo[]{FlagInfo.of("y", wrapperPlayClientPlayerFlying.getLocation().getY())});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private void teleportBelow(ProtocolPlayer protocolPlayer) {
        if (System.currentTimeMillis() - this.lastTeleport < 1000L) {
            return;
        }
        this.lastTeleport = System.currentTimeMillis();
        protocolPlayer.getPlayer().ifPresent(player -> FoliaScheduler.getEntityScheduler().run((Entity)player, this.getOptions().getCheckOptions().getPlugin(), object -> {
            if (player.getLocation().getY() > (double)this.max) {
                player.teleport(player.getLocation().clone().subtract(0.0, (double)(this.max - 5), 0.0));
            }
        }, () -> {}));
    }

    private static we a(we we2) {
        return we2;
    }
}

