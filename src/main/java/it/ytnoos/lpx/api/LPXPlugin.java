/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package it.ytnoos.lpx.api;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public interface LPXPlugin
extends Plugin {
    public Logger getLogger();

    public File getDataFolder();

    public File getConfigFile();

    public InputStream getResource(String var1);

    public PluginDescriptionFile getDescription();

    public Server getServer();

    public FileConfiguration getConfig();

    public void reload();

    public void saveDefaultConfig();

    public void saveConfig();
}

