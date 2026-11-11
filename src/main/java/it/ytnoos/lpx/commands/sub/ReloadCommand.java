/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 */
package it.ytnoos.lpx.commands.sub;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand
extends SubCommand {
    public ReloadCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "reload", "lpx.reload", "Reload configuration", false);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        this.lpxBukkit.reloadConfig();
        this.sendMessage(commandSender, String.valueOf(ChatColor.GREEN) + "Config reloaded!");
    }
}

