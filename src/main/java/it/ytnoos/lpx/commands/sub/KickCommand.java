/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.commands.sub;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.SubCommand;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand
extends SubCommand {
    public KickCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "kick", "lpx.kick", "Force close player connection", false, "<player> <message>");
    }

    private static String joinMessage(String[] stringArray) {
        String[] stringArray2 = stringArray;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < stringArray2.length; ++i) {
            stringBuilder.append(stringArray2[i]);
            if (i == stringArray2.length - 1) continue;
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static void runCommand(LPXBukkit lPXBukkit, CommandSender commandSender, String[] stringArray) {
        String[] stringArray2 = stringArray;
        if (stringArray2.length < 2) {
            MessageFormatter.send(commandSender, lPXBukkit.getConfigManager().getMessagesConfig().getPrefix() + String.valueOf(ChatColor.RED) + " Syntax: /lpx kick <player> <message>");
            return;
        }
        Player player = Bukkit.getPlayerExact((String)stringArray2[1]);
        if (player == null) {
            MessageFormatter.send(commandSender, lPXBukkit.getConfigManager().getMessagesConfig().getPrefix() + String.valueOf(ChatColor.RED) + " " + stringArray2[1] + " not found!");
            return;
        }
        String string = stringArray2.length > 2 ? KickCommand.joinMessage(stringArray2) : lPXBukkit.getConfigManager().getMessagesConfig().getKick();
        lPXBukkit.getLogger().info(() -> "Force kicking " + player.getName() + "...");
        lPXBukkit.kick(player, string);
    }

    @Override
    public void execute(CommandSender commandSender, String[] stringArray) {
        String[] stringArray2 = stringArray;
        KickCommand.runCommand(this.lpxBukkit, commandSender, stringArray2);
    }

    @Override
    public List tabComplete(CommandSender commandSender, String[] stringArray) {
        String[] stringArray2 = stringArray;
        if (stringArray2.length != 2) {
            return List.of();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = stringArray2[1];
        for (Player player : Bukkit.getOnlinePlayers()) {
            Player player2;
            if (commandSender instanceof Player && !(player2 = (Player)commandSender).canSee(player) || !player.getName().regionMatches(true, 0, string, 0, string.length())) continue;
            arrayList.add(player.getName());
        }
        return arrayList;
    }

    private static we a(we we2) {
        return we2;
    }
}

