/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import it.ytnoos.lpx.we;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigSection {
    protected final LPXPlugin plugin;
    protected final String key;
    @Nullable
    private MessagesConfig messagesConfig;

    public ConfigSection(LPXPlugin lPXPlugin, String string, @Nullable MessagesConfig messagesConfig) {
        this.plugin = lPXPlugin;
        this.key = string;
        this.messagesConfig = messagesConfig;
    }

    public void setMessagesConfig(@Nullable MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    public FileConfiguration getConfig() {
        return this.plugin.getConfig();
    }

    protected String eu(String string) {
        return this.eA(this.getConfig().getString(this.key + "." + string));
    }

    protected boolean getBoolean(String string) {
        return this.getConfig().getBoolean(this.key + "." + string);
    }

    protected boolean getBoolean(String string, boolean bl) {
        return this.getConfig().getBoolean(this.key + "." + string, bl);
    }

    protected long getLong(String string, long l) {
        return this.getConfig().getLong(this.key + "." + string, l);
    }

    protected int getInt(String string) {
        return this.getConfig().getInt(this.key + "." + string);
    }

    protected List getStringList(String string) {
        List list = this.getConfig().getStringList(this.key + "." + string);
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, this.eA((String)list.get(i)));
        }
        return list;
    }

    private String eA(String string) {
        String string2 = string;
        if (string2 == null) {
            return this.key + "." + this.key;
        }
        if (this.messagesConfig != null) {
            string2 = string2.replace("%main-color%", this.messagesConfig.getMainColor());
            string2 = string2.replace("%second-color%", this.messagesConfig.getSecondColor());
            if (this.messagesConfig.getPrefix() != null) {
                string2 = string2.replace("%prefix%", this.messagesConfig.getPrefix());
            }
        }
        return this.e8(string2);
    }

    private String e8(String string) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

    private static we b(we we2) {
        return we2;
    }
}

