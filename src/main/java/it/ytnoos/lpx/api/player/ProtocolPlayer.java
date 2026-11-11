/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.api.player;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.check.CheckResultInfo;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.we;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;

public interface ProtocolPlayer {
    public Optional<Player> getPlayer();

    public User getUser();

    public String getUsername();

    public UUID getUniqueId();

    public PrinterData getPrinterData();

    public boolean isBedrock();

    public void setBedrock(boolean var1);

    public boolean isProcessingPayload();

    public void setProcessingPayload(boolean var1);

    public Map<Object, CheckContainer<?>> getChecks();

    public CheckContainer<?> getChecks(Object var1);

    public boolean hasAnyCheck(Object var1, boolean var2);

    public CheckResultInfo handleChecks(Object var1, Object var2);

    public CheckResultInfo handleChecks(Object var1, Object var2, boolean var3);

    public <T> Check<T> getCheck(Object var1, CheckType var2, LPX var3);

    public ReceivedPacket getLastReceivedPacket();

    public long getLastAlert();

    public long getLastPunish();

    public long getLastTeleport();

    public void updateLastAlert();

    public long getLastPortalTeleport();

    public long getLastShears();

    public long getLastContainerInteract();

    public void setLastReceivedPacket(ReceivedPacket var1);

    public void updateLastPunish();

    public void updateLastTeleport();

    public void updateLastPortalTeleport();

    public void updateLastShears();

    public void updateLastContainerInteract();

    public long incrementPacketCount();

    public long getPacketCount();

    public long getLastKeepAliveSent();

    public void setLastKeepAliveSent(long var1);

    public double getPlayerPing();

    public void setPlayerPing(double var1);

    public void flag(Check<?> var1, Object var2, FlagInfo ... var3);

    public void punish(Check<?> var1);

    public void kick(String var1);

    public boolean isKicked();

    public boolean ignoreChecks();

    public boolean hasExemptPermission();

    default public boolean usesComponents() {
        return this.getUser().getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_20_5);
    }

    default public boolean usesHashedStacks() {
        return this.getUser().getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_21_5);
    }

    default public boolean serverUsesHashedStacks() {
        return PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5);
    }

    default public boolean isLegacyClientOnLegacyServer() {
        return !this.usesHashedStacks() && !this.serverUsesHashedStacks();
    }

    default public boolean isLegacyClientOnHashedServer() {
        return !this.usesHashedStacks() && this.serverUsesHashedStacks();
    }

    default public boolean isHashedClientOnLegacyServer() {
        return this.usesHashedStacks() && !this.serverUsesHashedStacks();
    }

    default public boolean isHashedClientOnHashedServer() {
        return this.usesHashedStacks() && this.serverUsesHashedStacks();
    }

    default public double itemFloodMultiplier() {
        if (this.isLegacyClientOnHashedServer()) {
            return 1.5;
        }
        if (this.isHashedClientOnHashedServer()) {
            return 0.5;
        }
        return 1.0;
    }

    public LPX lpx();

    private static we b(we we2) {
        return we2;
    }
}

