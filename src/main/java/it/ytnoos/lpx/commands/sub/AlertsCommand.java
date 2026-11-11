/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.commands.sub;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.SubCommand;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand
extends SubCommand {
    public AlertsCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "alerts", lPXBukkit.getConfigManager().getMessagesConfig().getAlertsConfig().getPermission(), "Toggle alerts", true);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        boolean bl = this.lpxBukkit.getAlertManager().toggleAlerts((Player)commandSender);
        if (bl) {
            MessageFormatter.send(commandSender, this.lpxBukkit.getConfigManager().getMessagesConfig().getAlertsConfig().getEnabled());
        } else {
            MessageFormatter.send(commandSender, this.lpxBukkit.getConfigManager().getMessagesConfig().getAlertsConfig().getDisabled());
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

