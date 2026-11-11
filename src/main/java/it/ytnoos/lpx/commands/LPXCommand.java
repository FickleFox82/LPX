/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package it.ytnoos.lpx.commands;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.SubCommand;
import it.ytnoos.lpx.commands.sub.AlertsCommand;
import it.ytnoos.lpx.commands.sub.GuiCommand;
import it.ytnoos.lpx.commands.sub.HealthCommand;
import it.ytnoos.lpx.commands.sub.KickCommand;
import it.ytnoos.lpx.commands.sub.MonitorCommand;
import it.ytnoos.lpx.commands.sub.ReloadCommand;
import it.ytnoos.lpx.commands.sub.WatchdogCommand;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LPXCommand
implements CommandExecutor,
TabCompleter {
    private static final boolean t = PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_19_1);
    private final LPXBukkit lpxBukkit;
    private final PluginCommand lpx;
    private final Map<String, SubCommand> subCommands = new LinkedHashMap<String, SubCommand>();

    public LPXCommand(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        this.lpx = ((JavaPlugin)lPXBukkit.getPlugin()).getCommand("lpx");
        this.lpx.setExecutor((CommandExecutor)this);
        this.lpx.setTabCompleter((TabCompleter)this);
        this.sC(new ReloadCommand(lPXBukkit));
        this.sC(new GuiCommand(lPXBukkit));
        this.sC(new AlertsCommand(lPXBukkit));
        this.sC(new KickCommand(lPXBukkit));
        this.sC(new MonitorCommand(lPXBukkit));
        this.sC(new HealthCommand(lPXBukkit));
        this.sC(new WatchdogCommand(lPXBukkit));
        this._v();
    }

    private void sC(SubCommand subCommand) {
        this.subCommands.put(subCommand.LB().toLowerCase(Locale.ROOT), subCommand);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (!this.hasAnyPermission(commandSender)) {
            MessageFormatter.send(commandSender, this.getUnknownCommandMessage());
            return true;
        }
        boolean bl = this.hasAnyPermission2(commandSender);
        if (stringArray.length == 0) {
            String string2;
            String string3 = this.lpxBukkit.getConfigManager().getMessagesConfig().getMainColor();
            String string4 = string2 = this.lpxBukkit.getConfigManager().getMessagesConfig().getSecondColor();
            String string5 = this.lpxBukkit.getPlugin().getDescription().getVersion().replace('-', ' ');
            String string6 = string3;
            String string7 = string3 + "\u25cf<reset><gray> Running " + string6 + "<bold>LPX " + string5 + "<reset><gray> by <reset>" + string4 + "Ytnoos";
            MessageFormatter.send(commandSender, string7);
            if (bl) {
                commandSender.sendMessage("");
            }
            for (SubCommand subCommand : this.subCommands.values()) {
                if (!commandSender.hasPermission(subCommand.LU()) || subCommand.isPlayerOnly() && !(commandSender instanceof Player)) continue;
                String string8 = subCommand.LS();
                String string9 = subCommand.L0();
                String string10 = subCommand.LB();
                String string11 = string2;
                MessageFormatter.send(commandSender, string3 + "<bold>> <reset>" + string11 + "/lpx " + string10 + " " + string9 + " <reset><gray>- <reset><white>" + string8);
            }
            return true;
        }
        SubCommand subCommand = this.Ot(stringArray[0]);
        if (subCommand == null) {
            MessageFormatter.send(commandSender, this.lpxBukkit.getConfigManager().getMessagesConfig().getInvalidArguments());
            return true;
        }
        if (!commandSender.hasPermission(subCommand.LU())) {
            MessageFormatter.send(commandSender, this.lpxBukkit.getConfigManager().getMessagesConfig().getNoPermission().replace("%permission%", subCommand.LU()));
            return true;
        }
        if (subCommand.isPlayerOnly() && !(commandSender instanceof Player)) {
            MessageFormatter.send(commandSender, this.lpxBukkit.getConfigManager().getMessagesConfig().getPrefix() + String.valueOf(ChatColor.RED) + " You must be a player to execute this command!");
            return true;
        }
        subCommand.execute(commandSender, stringArray);
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (!this.hasAnyPermission(commandSender)) {
            return Collections.emptyList();
        }
        if (stringArray.length == 1) {
            ArrayList<String> arrayList = new ArrayList<String>();
            String string2 = stringArray[0];
            for (SubCommand subCommand : this.subCommands.values()) {
                if (!this.dr(commandSender, subCommand) || !subCommand.LB().regionMatches(true, 0, string2, 0, string2.length())) continue;
                arrayList.add(subCommand.LB());
            }
            return arrayList;
        }
        SubCommand subCommand = this.Ot(stringArray[0]);
        if (subCommand == null || !this.dr(commandSender, subCommand)) {
            return Collections.emptyList();
        }
        return subCommand.tabComplete(commandSender, stringArray);
    }

    private String getUnknownCommandMessage() {
        String string;
        try {
            string = this.lpxBukkit.getPlugin().getServer().spigot().getSpigotConfig().getString("messages.unknown-command");
        }
        catch (Throwable throwable) {
            string = this.lpxBukkit.getConfigManager().getMessagesConfig().getUnknownCommand();
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

    private SubCommand Ot(String string) {
        return this.subCommands.get(string.toLowerCase(Locale.ROOT));
    }

    public boolean hasAnyPermission(CommandSender commandSender) {
        return !this.lpxBukkit.getConfigManager().getOptionsConfig().isHiddenCommand() || this.hasAnyPermission2(commandSender);
    }

    public void _v() {
        boolean bl = this.lpxBukkit.getConfigManager().getOptionsConfig().isHiddenCommand() && t;
        this.lpx.setPermission(bl ? String.join((CharSequence)";", this._1()) : null);
        this.lpx.setPermissionMessage(bl ? "" : null);
    }

    private List _1() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (SubCommand subCommand : this.subCommands.values()) {
            if (arrayList.contains(subCommand.LU())) continue;
            arrayList.add(subCommand.LU());
        }
        return arrayList;
    }

    private boolean hasAnyPermission2(CommandSender commandSender) {
        for (SubCommand subCommand : this.subCommands.values()) {
            if (!commandSender.hasPermission(subCommand.LU())) continue;
            return true;
        }
        return false;
    }

    private boolean dr(CommandSender commandSender, SubCommand subCommand) {
        return commandSender.hasPermission(subCommand.LU()) && (!subCommand.isPlayerOnly() || commandSender instanceof Player);
    }

    private static we a(we we2) {
        return we2;
    }
}

