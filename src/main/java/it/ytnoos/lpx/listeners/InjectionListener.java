/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelPipeline
 */
package it.ytnoos.lpx.listeners;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.UserDisconnectEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import it.ytnoos.lpx.listeners.PacketDecoder;
import it.ytnoos.lpx.player.BukkitProtocolPlayer;
import it.ytnoos.lpx.we;
import java.util.UUID;

public class InjectionListener
extends PacketListenerAbstract {
    private static final String HANDLER_NAME = "lpx-handler";
    private final LPXBukkit lpxBukkit;
    private final PlayerRegistry registry;

    public InjectionListener(LPXBukkit lPXBukkit, PlayerRegistry playerRegistry) {
        this.lpxBukkit = lPXBukkit;
        this.registry = playerRegistry;
    }

    @Override
    public void onUserDisconnect(UserDisconnectEvent userDisconnectEvent) {
        this.registry.uninject(userDisconnectEvent.getUser());
    }

    @Override
    public void onPacketSend(PacketSendEvent packetSendEvent) {
        if (packetSendEvent.getPacketType() == PacketType.Login.Server.LOGIN_SUCCESS) {
            packetSendEvent.getTasksAfterSend().add(() -> this.setup(packetSendEvent.getUser(), (Channel)packetSendEvent.getChannel()));
        }
    }

    private void setup(User user, Channel channel) {
        UUID uUID = user.getUUID();
        this.lpxBukkit.debug(() -> Thread.currentThread().getName() + " Setting up player with uuid " + String.valueOf(uUID));
        BukkitProtocolPlayer bukkitProtocolPlayer = new BukkitProtocolPlayer(this.lpxBukkit, user);
        ProtocolPlayer protocolPlayer = this.registry.addPlayer(user, bukkitProtocolPlayer);
        if (protocolPlayer != null) {
            this.lpxBukkit.getLogger().warning("Player " + user.getName() + " already injected, removed old instance.");
        }
        if (this.lpxBukkit.getConfigManager().getOptionsConfig().isGeyser() && uUID.getMostSignificantBits() == 0L) {
            bukkitProtocolPlayer.setBedrock(true);
        }
        this.inject(channel, user, bukkitProtocolPlayer);
    }

    public void inject(Channel channel, User user, ProtocolPlayer protocolPlayer) {
        this.lpxBukkit.debug(() -> Thread.currentThread().getName() + " Injecting player: " + user.getName() + " " + String.valueOf(user.getUUID()));
        ChannelPipeline channelPipeline = channel.pipeline();
        Object object = channelPipeline.get("lpx-handler");
        if (object != null) {
            channelPipeline.remove(object);
        } else {
            object = new PacketDecoder(this.lpxBukkit, protocolPlayer);
        }
        channelPipeline.addAfter("decoder", "lpx-handler", object);
        this.lpxBukkit.debug(() -> user.getName() + " : " + String.valueOf(channelPipeline.toMap()));
        this.lpxBukkit.getPacketTickMonitor().hM(protocolPlayer.getUniqueId(), Thread.currentThread(), channel.eventLoop());
    }

    private static we a(we we2) {
        return we2;
    }
}

