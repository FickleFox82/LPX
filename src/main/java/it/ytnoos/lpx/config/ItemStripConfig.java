/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import java.util.HashSet;
import java.util.Set;

public class ItemStripConfig
extends ConfigSection {
    private static final String SECTION = "item-strip";
    private final boolean enabled = this.getBoolean("enabled");
    private final boolean setSlot = this.getBoolean("set-slot");
    private final Set<String> p = new HashSet<String>(this.getStringList("remove-components"));
    private final Set<String> I = new HashSet<String>(this.getStringList("remove-nbt"));

    public ItemStripConfig(LPXPlugin lPXPlugin, String string, MessagesConfig messagesConfig) {
        super(lPXPlugin, string + ".item-strip", messagesConfig);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isSetSlot() {
        return this.setSlot;
    }

    public Set k4() {
        return this.p;
    }

    public Set kT() {
        return this.I;
    }
}

