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
import it.ytnoos.lpx.we;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MonitorCommand
extends SubCommand {
    public MonitorCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "monitor", "lpx.metrics", "Turn off/on the PTD Monitor", false);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        FoliaScheduler.getAsyncScheduler().runNow(this.lpxBukkit.getPlugin(), object -> {
            if (this.lpxBukkit.getPacketTickMonitor().isRunning()) {
                this.lpxBukkit.getPacketTickMonitor().pj();
                this.sendMessage(commandSender, String.valueOf(ChatColor.RED) + "Monitor disabled.");
            } else {
                this.lpxBukkit.getPacketTickMonitor().pc();
                this.sendMessage(commandSender, String.valueOf(ChatColor.GREEN) + "Monitor enabled, it's not recommended to keep it enabled forever!");
            }
        });
    }

    private static we a(we we2) {
        return we2;
    }
}

