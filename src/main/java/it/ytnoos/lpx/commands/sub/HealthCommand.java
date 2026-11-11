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
import it.ytnoos.lpx.monitor.HealthReport;
import it.ytnoos.lpx.monitor.PacketTickMonitor;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HealthCommand
extends SubCommand {
    private final PacketTickMonitor packetTickMonitor;
    private final HealthReport healthReport;

    public HealthCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "health", "lpx.health", "View the netty health report", false);
        this.packetTickMonitor = lPXBukkit.getPacketTickMonitor();
        this.healthReport = new HealthReport(lPXBukkit);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        if (!this.packetTickMonitor.isRunning()) {
            MessageFormatter.send(commandSender, String.valueOf(ChatColor.RED) + "Monitor is not running. Enable it with /lpx monitor");
            return;
        }
        FoliaScheduler.getAsyncScheduler().runNow(this.lpxBukkit.getPlugin(), object -> {
            String string = this.healthReport.eX(this.packetTickMonitor);
            MessageFormatter.send(commandSender, string);
        });
    }

    private static we a(we we2) {
        return we2;
    }
}

