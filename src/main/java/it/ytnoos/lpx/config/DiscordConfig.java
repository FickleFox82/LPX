/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import java.util.List;

public class DiscordConfig
extends ConfigSection {
    private static final String u = "discord";
    private final boolean enabled = this.getBoolean("enabled");
    private final String url = this.eu("url");
    private final String s = this.getConfig().getString(this.key + ".color");
    private final List<String> content = this.getStringList("content");

    public DiscordConfig(MessagesConfig messagesConfig, LPXPlugin lPXPlugin) {
        super(lPXPlugin, "discord", messagesConfig);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getUrl() {
        return this.url;
    }

    public String Aj() {
        return this.s;
    }

    public List getContent() {
        return this.content;
    }
}

