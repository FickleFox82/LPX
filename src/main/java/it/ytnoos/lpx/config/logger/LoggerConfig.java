/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config.logger;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.config.logger.FilterMode;
import it.ytnoos.lpx.config.logger.OutputType;
import it.ytnoos.lpx.config.logger.WatchdogConfig;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoggerConfig
extends ConfigSection {
    private static final String SECTION = "packet-logger";
    private final long heavyPacketThreshold = this.getLong("heavy-packet-threshold", 5000000L);
    private final long heavyListedPacketThreshold = this.getLong("heavy-listed-packet-threshold", 50000000L);
    private final Set<String> d = new HashSet<String>(this.getStringList("heavy-ignored-packets"));
    private final Set<String> Q = new HashSet<String>(this.getStringList("heavy-listed-packets"));
    private final boolean enabled = this.getBoolean("enabled");
    private final OutputType outputType = this.aw(this.eu("output"));
    private final WatchdogConfig watchdogConfig;
    private final FilterMode n;
    private final List<String> players;
    private final FilterMode k;
    private final List<String> packets;

    public LoggerConfig(MessagesConfig messagesConfig, LPXPlugin lPXPlugin) {
        super(lPXPlugin, SECTION, messagesConfig);
        this.watchdogConfig = new WatchdogConfig(lPXPlugin, SECTION, messagesConfig);
        this.n = this.ai(this.eu("player-mode"));
        this.players = this.getStringList("players");
        this.k = this.ai(this.eu("packet-mode"));
        this.packets = this.getStringList("packets");
    }

    private FilterMode ai(String string) {
        try {
            return FilterMode.valueOf(string.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.plugin.getLogger().warning("Invalid logger type " + string + ", using default value (whitelist)");
            return FilterMode.WHITELIST;
        }
    }

    private OutputType aw(String string) {
        try {
            return OutputType.valueOf(string.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.plugin.getLogger().warning("Invalid output type " + string + ", using default value (file)");
            return OutputType.FILE;
        }
    }

    public boolean aP(String string) {
        if (this.n == FilterMode.WHITELIST) {
            return this.players.contains(string);
        }
        return !this.players.contains(string);
    }

    public boolean au(String string) {
        if (this.k == FilterMode.WHITELIST) {
            return this.packets.contains(string);
        }
        return !this.packets.contains(string);
    }

    public boolean isHeavy(long l) {
        if (this.heavyPacketThreshold == -1L) {
            return false;
        }
        return l >= this.heavyPacketThreshold;
    }

    public boolean eE(Object object, long l) {
        if (this.heavyListedPacketThreshold == -1L) {
            return false;
        }
        return this.Q.contains(object) && l >= this.heavyListedPacketThreshold;
    }

    public boolean ac(Object object) {
        return this.Q.contains(object);
    }

    public boolean aE(Object object) {
        return this.d.contains(object);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public WatchdogConfig getWatchdogConfig() {
        return this.watchdogConfig;
    }

    public OutputType getOutputType() {
        return this.outputType;
    }
}

