/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.RegisteredCheck;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.config.ConfigUpdater;
import it.ytnoos.lpx.config.DiscordConfig;
import it.ytnoos.lpx.config.logger.LoggerConfig;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import it.ytnoos.lpx.config.options.OptionsConfig;
import it.ytnoos.lpx.we;
import java.util.List;

public class ConfigManager {
    private MessagesConfig messagesConfig;
    private OptionsConfig optionsConfig;
    private DiscordConfig discordConfig;
    private LoggerConfig loggerConfig;

    public ConfigManager(LPXPlugin lPXPlugin) {
        lPXPlugin.saveDefaultConfig();
        ConfigUpdater.update(lPXPlugin);
    }

    public void dX(LPXPlugin lPXPlugin) {
        this.messagesConfig = new MessagesConfig(lPXPlugin);
        this.optionsConfig = new OptionsConfig(this.messagesConfig, lPXPlugin);
        this.loggerConfig = new LoggerConfig(this.messagesConfig, lPXPlugin);
        this.discordConfig = new DiscordConfig(this.messagesConfig, lPXPlugin);
    }

    public void Js(LPXBukkit lPXBukkit) {
        lPXBukkit.getPlugin().reload();
        this.dX(lPXBukkit.getPlugin());
        for (List<RegisteredCheck<?>> object : lPXBukkit.getCheckManager().getChecks().values()) {
            for (RegisteredCheck<?> registeredCheck : object) {
                registeredCheck.getOptions().reload();
            }
        }
        for (ProtocolPlayer protocolPlayer : lPXBukkit.getRegistry().getPlayers().values()) {
            for (CheckContainer checkContainer : protocolPlayer.getChecks().values()) {
                for (Check check : checkContainer) {
                    check.reload();
                }
            }
        }
        lPXBukkit.getAlertManager().tF();
    }

    public OptionsConfig getOptionsConfig() {
        return this.optionsConfig;
    }

    public MessagesConfig getMessagesConfig() {
        return this.messagesConfig;
    }

    public LoggerConfig getLoggerConfig() {
        return this.loggerConfig;
    }

    public DiscordConfig getDiscordConfig() {
        return this.discordConfig;
    }

    private static we a(we we2) {
        return we2;
    }
}

