/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandSendEvent
 */
package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.we;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandSendListener
implements Listener {
    private final LPXBukkit lpxBukkit;

    public CommandSendListener(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @EventHandler
    public void commandSend(PlayerCommandSendEvent playerCommandSendEvent) {
        if (!this.lpxBukkit.getLpxCommand().hasAnyPermission((CommandSender)playerCommandSendEvent.getPlayer())) {
            playerCommandSendEvent.getCommands().remove("lpx");
            playerCommandSendEvent.getCommands().remove("lpx:lpx");
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

