/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandler$Sharable
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToMessageDecoder
 */
package it.ytnoos.lpx.listeners;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.config.logger.LoggerConfig;
import it.ytnoos.lpx.we;
import java.util.List;

@ChannelHandler.Sharable
public class PacketDecoder
extends MessageToMessageDecoder<Object> {
    private final LPXBukkit lpxBukkit;
    private final ProtocolPlayer player;

    public PacketDecoder(LPXBukkit lPXBukkit, ProtocolPlayer protocolPlayer) {
        this.lpxBukkit = lPXBukkit;
        this.player = protocolPlayer;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) {
        ReceivedPacket receivedPacket = this.player.getLastReceivedPacket();
        if (receivedPacket != null) {
            this.player.setLastReceivedPacket(null);
            if (receivedPacket.getPacket() == null) {
                receivedPacket.setPacket(object);
            }
            this.lpxBukkit.getPacketTickMonitor().recordDelay((long)this.lpxBukkit.getPacketTickMonitor().getCurrentTick() - receivedPacket.getReceivedAtTick(), channelHandlerContext.channel().eventLoop());
            this.logPacket(receivedPacket);
        }
        list.add(object);
    }

    private void logPacket(ReceivedPacket receivedPacket) {
        String string;
        LoggerConfig loggerConfig = this.lpxBukkit.getConfigManager().getLoggerConfig();
        boolean bl = loggerConfig.ac(string = receivedPacket.getPacketType().getName()) ? loggerConfig.eE(string, receivedPacket.getElapsedTime()) : loggerConfig.isHeavy(receivedPacket.getElapsedTime());
        boolean bl2 = bl = bl && !loggerConfig.aE(string);
        if (!loggerConfig.isEnabled() && !bl) {
            return;
        }
        String string2 = receivedPacket.getPlayerName();
        if (!loggerConfig.aP(string2) && !bl) {
            return;
        }
        if (bl && this.player.getPacketCount() <= 5L) {
            return;
        }
        if ((loggerConfig.au(string) || bl && this.lpxBukkit.canPacketLogHeavy()) && !this.player.isKicked()) {
            this.lpxBukkit.getPacketLogger().logPacket(receivedPacket);
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

