/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.EventLoop
 *  org.bukkit.entity.Player
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.player;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import it.ytnoos.lpx.listeners.InjectionListener;
import it.ytnoos.lpx.we;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerRegistryImpl
implements PlayerRegistry {
    private final LPXBukkit lpxBukkit;
    private final Map<User, ProtocolPlayer> players = new ConcurrentHashMap<User, ProtocolPlayer>();

    public PlayerRegistryImpl(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        PacketEvents.getAPI().getEventManager().registerListener(new InjectionListener(lPXBukkit, this));
    }

    @Override
    public void uninjectAll() {
        HashSet<User> hashSet = new HashSet<User>(this.players.keySet());
        for (User user : hashSet) {
            this.uninject(user);
        }
    }

    @Override
    public void uninject(User user) {
        ProtocolPlayer protocolPlayer = this.players.remove(user);
        if (protocolPlayer == null) {
            return;
        }
        UUID uUID = user.getUUID();
        if (uUID == null) {
            return;
        }
        this.lpxBukkit.getAlertManager().vQ(uUID);
        Object object = user.getChannel();
        if (object == null) {
            return;
        }
        EventLoop eventLoop = ((Channel)object).eventLoop();
        if (eventLoop == null) {
            return;
        }
        eventLoop.execute(() -> this.lpxBukkit.getPacketTickMonitor().ht(uUID, Thread.currentThread(), eventLoop));
    }

    @Override
    public Optional<ProtocolPlayer> getPlayer(Player player) {
        User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
        if (user == null) {
            return Optional.empty();
        }
        return this.getPlayer(user);
    }

    @Override
    public Optional<ProtocolPlayer> getPlayer(User user) {
        return Optional.ofNullable(this.players.get(user));
    }

    @Override
    public Map<User, ProtocolPlayer> getPlayers() {
        return Collections.unmodifiableMap(this.players);
    }

    @Override
    @Nullable
    public ProtocolPlayer addPlayer(User user, ProtocolPlayer protocolPlayer) {
        return this.players.put(user, protocolPlayer);
    }

    private static we a(we we2) {
        return we2;
    }
}

