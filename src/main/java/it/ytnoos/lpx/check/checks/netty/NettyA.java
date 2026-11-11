/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package it.ytnoos.lpx.check.checks.netty;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import io.netty.buffer.ByteBuf;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class NettyA
extends PlayerCheck<ByteBuf> {
    private int maxSize;
    private final boolean preVia;

    public NettyA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions, boolean bl) {
        super(protocolPlayer, checkOptions);
        this.preVia = bl;
    }

    @Override
    public void loadOptions() {
        this.maxSize = this.getBoolean("automatic", true) ? this.getMaxSize() : this.getInt("max", 98304);
    }

    private int getMaxSize() {
        ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        if (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
            return 8448;
        }
        if (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_12_2)) {
            return 24576;
        }
        return 98304;
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, ByteBuf byteBuf) {
        long l = protocolPlayer.getLastReceivedPacket().getSize();
        long l2 = this.maxSize;
        if (!this.preVia) {
            l2 *= 2L;
        }
        if (l < 0L || l >= l2) {
            this.Fn(byteBuf, new FlagInfo[]{FlagInfo.of("size", l), FlagInfo.of("temp-max-size", l2), FlagInfo.of("max-size", this.maxSize)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return this.preVia;
    }

    private static we a(we we2) {
        return we2;
    }
}

