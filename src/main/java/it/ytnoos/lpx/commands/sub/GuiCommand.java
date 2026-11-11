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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand
extends SubCommand {
    public GuiCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "gui", "lpx.gui.main", "Open GUI", true);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        this.lpxBukkit.getInventoryContainer().Bu().open((Player)commandSender);
    }
}

