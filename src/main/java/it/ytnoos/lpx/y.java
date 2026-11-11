/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

abstract class y
extends PlayerCheck<PacketReceiveEvent> {
    private final PacketTypeCommon packetTypeCommon;

    protected y(ProtocolPlayer protocolPlayer, CheckOptions checkOptions, PacketTypeCommon packetTypeCommon) {
        super(protocolPlayer, checkOptions);
        this.packetTypeCommon = packetTypeCommon;
    }

    public CheckResult XD(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (packetReceiveEvent.getPacketType() != this.packetTypeCommon) {
            return CheckResult.ALLOW;
        }
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null || player.getGameMode() == GameMode.CREATIVE) {
            return CheckResult.ALLOW;
        }
        this.Fn(packetReceiveEvent, new FlagInfo[0]);
        return CheckResult.DENY;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

