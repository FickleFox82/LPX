/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import it.ytnoos.lpx.we;
import java.util.List;

public class AlertsConfig
extends ConfigSection {
    private static final String h = "alerts";
    private final String permission = this.eu("permission");
    private final String format = this.eu("format");
    private final List<String> hover = this.getStringList("hover");
    private final String enabled = this.eu("enabled");
    private final String disabled = this.eu("disabled");

    public AlertsConfig(LPXPlugin lPXPlugin, MessagesConfig messagesConfig, String string) {
        super(lPXPlugin, string + ".alerts", messagesConfig);
    }

    public String getPermission() {
        return this.permission;
    }

    public String getFormat() {
        return this.format;
    }

    public List getHover() {
        return this.hover;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public String getDisabled() {
        return this.disabled;
    }

    private static we a(we we2) {
        return we2;
    }
}

