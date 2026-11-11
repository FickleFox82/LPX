/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.HoverEvent
 *  net.md_5.bungee.api.chat.HoverEvent$Action
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package it.ytnoos.lpx.alert;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.manager.server.VersionComparison;
import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alert.DiscordAlerts;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.event.LPXAlertEvent;
import it.ytnoos.lpx.api.event.LPXPunishEvent;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.PendingPunishment;
import it.ytnoos.lpx.commands.sub.KickCommand;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.config.AlertsConfig;
import it.ytnoos.lpx.logger.LogWriter;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class AlertManager {
    private final LPXBukkit lpxBukkit;
    private final LogWriter logWriter;
    private final DiscordAlerts discordAlerts;
    private final Set<UUID> alertsDisabled = new HashSet<UUID>();
    private long lastAlert = 0L;

    public AlertManager(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        this.logWriter = new LogWriter(lPXBukkit);
        this.discordAlerts = new DiscordAlerts(lPXBukkit);
    }

    public void tw() {
        this.logWriter.KE();
        this.discordAlerts.KW();
    }

    public void tI() {
        this.logWriter.Ku();
    }

    public void flag(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfoArray) {
        BaseComponent[] baseComponentArray;
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        if (protocolPlayer.isKicked()) {
            return;
        }
        this.logWriter.writeFlag(protocolPlayer, check, flagInfoArray2);
        if (this.lpxBukkit.getRecentViolations().size() >= 360) {
            this.lpxBukkit.getRecentViolations().removeLast();
        }
        this.lpxBukkit.getRecentViolations().addFirst(new PendingPunishment(protocolPlayer.getUsername(), check.getOptions(), System.currentTimeMillis(), flagInfoArray2));
        this.callEvent(() -> Bukkit.getPluginManager().callEvent((Event)new LPXAlertEvent(protocolPlayer, check, flagInfoArray2)));
        int n = check.getOptions().getCheckOptions().getMinVl();
        if (n == -1 || check.getOptions().getVl() < n) {
            return;
        }
        AlertsConfig alertsConfig = this.lpxBukkit.getConfigManager().getMessagesConfig().getAlertsConfig();
        String string = this.replace(alertsConfig.getFormat(), protocolPlayer, check, flagInfoArray2);
        this.lpxBukkit.getLogger().log(Level.INFO, MessageFormatter.kB(string));
        if (TimeUtils.difference(protocolPlayer.getLastPunish()) < (long)this.lpxBukkit.getConfigManager().getOptionsConfig().getPunishDelay()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < alertsConfig.getHover().size(); ++i) {
            stringBuilder.append(this.replace((String)alertsConfig.getHover().get(i), protocolPlayer, check, flagInfoArray2));
            if (i == alertsConfig.getHover().size() - 1) continue;
            stringBuilder.append('\n');
        }
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, MessageFormatter.toBungee(stringBuilder.toString()));
        for (BaseComponent baseComponent : baseComponentArray = MessageFormatter.toBungee(string)) {
            baseComponent.setHoverEvent(hoverEvent);
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            block0: {
                if (!player.hasPermission(alertsConfig.getPermission()) || this.alertsDisabled.contains(player.getUniqueId())) break block0;
                player.getPlayer().spigot().sendMessage(baseComponentArray);
            }
        });
        FoliaScheduler.getAsyncScheduler().runNow(this.lpxBukkit.getPlugin(), object -> this.discordAlerts.sendAlert(protocolPlayer, check, flagInfoArray2));
    }

    public void punish(ProtocolPlayer protocolPlayer, Check check) {
        if (TimeUtils.difference(protocolPlayer.getLastPunish()) < (long)this.lpxBukkit.getConfigManager().getOptionsConfig().getPunishDelay()) {
            return;
        }
        this.callEvent(() -> Bukkit.getPluginManager().callEvent((Event)new LPXPunishEvent(protocolPlayer, check)));
        protocolPlayer.updateLastPunish();
        for (String string : check.getOptions().getCheckOptions().getPunishCommands()) {
            if ((string = this.replacePlayer(string, protocolPlayer)).startsWith("lpx kick")) {
                KickCommand.runCommand(this.lpxBukkit, (CommandSender)Bukkit.getConsoleSender(), string.substring(4).split(" "));
                continue;
            }
            String string2 = string;
            FoliaScheduler.getGlobalRegionScheduler().execute(this.lpxBukkit.getPlugin(), () -> Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)string2));
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            block0: {
                if (!player.hasPermission(this.lpxBukkit.getConfigManager().getMessagesConfig().getAlertsConfig().getPermission()) || this.alertsDisabled.contains(player.getUniqueId())) break block0;
                MessageFormatter.send(player.getPlayer(), this.replace(this.lpxBukkit.getConfigManager().getMessagesConfig().getKickAlert(), protocolPlayer, check, new FlagInfo[0]));
            }
        });
    }

    public String replace(String string, ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        return this.replaceFlagInfo(this.replacePlayer(this.replaceServer(string), protocolPlayer), check, flagInfoArray2);
    }

    public String replacePlayer(String string, ProtocolPlayer protocolPlayer) {
        return string.replace("%player%", protocolPlayer.getUsername()).replace("%uuid%", protocolPlayer.getUniqueId().toString());
    }

    private String replaceFlagInfo(String string, Check check, FlagInfo[] flagInfoArray) {
        FlagInfo[] flagInfoArray2 = flagInfoArray;
        StringBuilder stringBuilder = new StringBuilder(flagInfoArray2.length == 0 ? ":(" : "\n");
        for (FlagInfo flagInfo : flagInfoArray2) {
            stringBuilder.append(this.lpxBukkit.getConfigManager().getMessagesConfig().getSecondColor()).append(flagInfo.getName()).append(": ").append(ChatColor.GRAY).append(MessageFormatter.kp(String.valueOf(flagInfo.getValue()))).append('\n');
        }
        return string.replace("%check%", check.getType().getCategory().getFormattedName()).replace("%type%", check.getType().getId().name()).replace("%vl%", "" + check.getOptions().getVl()).replace("%infos%", stringBuilder.toString()).replace("%description%", check.getType().getDescription()).replace("%max-vl%", "" + check.getOptions().getCheckOptions().getMaxVL());
    }

    private void callEvent(Runnable runnable) {
        if (PacketEvents.getAPI().getServerManager().getVersion().is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_13)) {
            FoliaScheduler.getGlobalRegionScheduler().execute(this.lpxBukkit.getPlugin(), runnable);
        } else {
            runnable.run();
        }
    }

    private String replaceServer(String string) {
        return string.replace("%server%", this.lpxBukkit.getServer());
    }

    public void sendPermissionAlert(String string, String string2) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            block0: {
                if (!player.hasPermission(string2) || this.alertsDisabled.contains(player.getUniqueId())) break block0;
                MessageFormatter.send(player, string);
            }
        });
        this.lpxBukkit.getLogger().log(Level.INFO, MessageFormatter.kB(string));
    }

    public boolean toggleAlerts(Player player) {
        UUID uUID = player.getUniqueId();
        if (this.alertsDisabled.contains(uUID)) {
            this.alertsDisabled.remove(uUID);
            return true;
        }
        this.alertsDisabled.add(uUID);
        return false;
    }

    public void vQ(Object object) {
        this.alertsDisabled.remove(object);
    }

    public void tF() {
        this.discordAlerts.KW();
    }

    public LogWriter getLogWriter() {
        return this.logWriter;
    }

    public Set getAlertsDisabled() {
        return this.alertsDisabled;
    }

    private static we a(we we2) {
        return we2;
    }
}

