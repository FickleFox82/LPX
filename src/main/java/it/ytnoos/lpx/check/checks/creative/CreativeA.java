/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.creative;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CreativeA
extends PlayerCheck<PacketReceiveEvent> {
    public CreativeA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult ch(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (packetReceiveEvent.getPacketType() != PacketType.Play.Client.CREATIVE_INVENTORY_ACTION) {
            return CheckResult.ALLOW;
        }
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!protocolPlayer.getPrinterData().isUsingPrinter() && player.getGameMode() != GameMode.CREATIVE) {
            this.Fn(packetReceiveEvent, new FlagInfo[0]);
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

