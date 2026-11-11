/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 */
package it.ytnoos.lpx.commands.sub;

import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.SubCommand;
import it.ytnoos.lpx.watchdog.NettyWatchdog;
import it.ytnoos.lpx.we;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class WatchdogCommand
extends SubCommand {
    public WatchdogCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "watchdog", "lpx.watchdog", "Toggle netty watchdog", false);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        FoliaScheduler.getAsyncScheduler().runNow(this.lpxBukkit.getPlugin(), object -> {
            NettyWatchdog nettyWatchdog = this.lpxBukkit.getNettyWatchdog();
            if (nettyWatchdog.isRunning()) {
                this.sendMessage(commandSender, String.valueOf(ChatColor.YELLOW) + "Stopping the watchdog thread...");
            } else {
                this.sendMessage(commandSender, String.valueOf(ChatColor.YELLOW) + "Starting the watchdog thread...");
                this.sendMessage(commandSender, String.valueOf(ChatColor.RED) + "WARNING! Do not use this in production because it may cause TPS Drops!");
            }
            this.lpxBukkit.getNettyWatchdog().uG();
            this.sendMessage(commandSender, nettyWatchdog.isRunning() ? String.valueOf(ChatColor.GREEN) + "Watchdog thread started!" : String.valueOf(ChatColor.RED) + "Watchdog thread stopped!");
        });
    }

    private static we a(we we2) {
        return we2;
    }
}

