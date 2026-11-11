/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.event.player.PlayerLoginEvent$Result
 */
package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.we;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLegacyListener
implements Listener {
    private final LPXBukkit lpxBukkit;

    public PlayerLegacyListener(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent playerLoginEvent) {
        if (playerLoginEvent.getAddress() == null) {
            playerLoginEvent.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            playerLoginEvent.setKickMessage(this.lpxBukkit.getConfigManager().getMessagesConfig().getNullAddress());
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

