/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.api.protocol;

import com.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface PlayerRegistry {
    public Optional<ProtocolPlayer> getPlayer(User var1);

    public Map<User, ProtocolPlayer> getPlayers();

    public void uninjectAll();

    public void uninject(User var1);

    public Optional<ProtocolPlayer> getPlayer(Player var1);

    @Nullable
    public ProtocolPlayer addPlayer(User var1, ProtocolPlayer var2);
}

