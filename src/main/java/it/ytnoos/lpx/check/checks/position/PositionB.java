/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.position;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.we;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PositionB
extends PlayerCheck<WrapperPlayClientPlayerFlying> {
    private int delay;
    private boolean hard;
    private boolean ignoreFly;
    private int G;
    private int J;
    private String lastWorld = "";

    public PositionB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.delay = this.getInt("delay", 2000);
        this.hard = this.getBoolean("hard", false);
        this.ignoreFly = this.getBoolean("ignore-fly", true);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!wrapperPlayClientPlayerFlying.hasPositionChanged()) {
            return CheckResult.ALLOW;
        }
        if (this.ignoreFly && player.isFlying() || TimeUtils.difference(protocolPlayer.getLastTeleport()) < (long)this.delay) {
            this.updateChunk(player, wrapperPlayClientPlayerFlying);
            return CheckResult.ALLOW;
        }
        int n = (int)wrapperPlayClientPlayerFlying.getLocation().getX() / 16;
        int n2 = (int)wrapperPlayClientPlayerFlying.getLocation().getZ() / 16;
        World world = player.getWorld();
        String string = world.getName();
        boolean bl = true;
        if (!world.isChunkLoaded(n, n2)) {
            if ((this.G != n || this.J != n2) && this.lastWorld.equals(string)) {
                this.F6(wrapperPlayClientPlayerFlying, new FlagInfo[]{FlagInfo.of("x", wrapperPlayClientPlayerFlying.getLocation().getX()), FlagInfo.of("z", wrapperPlayClientPlayerFlying.getLocation().getZ()), FlagInfo.of("chunk-x", n), FlagInfo.of("chunk-z", n2), FlagInfo.of("last-chunk-x", this.G), FlagInfo.of("last-chunk-z", this.J), FlagInfo.of("world", string)});
                if (this.hard) {
                    bl = false;
                }
            } else {
                this.zl();
            }
        }
        this.updateChunk(player, wrapperPlayClientPlayerFlying);
        return bl ? CheckResult.ALLOW : CheckResult.DENY;
    }

    private void updateChunk(Player player, WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        this.lastWorld = player.getWorld().getName();
        this.G = (int)wrapperPlayClientPlayerFlying.getLocation().getX() / 16;
        this.J = (int)wrapperPlayClientPlayerFlying.getLocation().getZ() / 16;
    }

    private static we a(we we2) {
        return we2;
    }
}

