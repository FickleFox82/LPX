/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package it.ytnoos.lpx.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig {
    private final File file;
    private final String name;
    private FileConfiguration configuration;

    public CustomConfig(JavaPlugin javaPlugin, String object, boolean bl) {
        if (!((String)object).endsWith(".yml")) {
            object = (String)object + ".yml";
        }
        this.name = object;
        if (bl) {
            File file = Bukkit.getServer().getWorldContainer();
            File file2 = file.getAbsoluteFile();
            String string = file2.getParentFile().getParent();
            this.file = new File(string + "/config/", (String)object);
        } else {
            this.file = new File(javaPlugin.getDataFolder(), (String)object);
        }
        this.load(javaPlugin);
    }

    private void load(JavaPlugin javaPlugin) {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            javaPlugin.saveResource(this.name, false);
        }
        this.configuration = new YamlConfiguration();
        try {
            this.configuration.load(this.file);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public int getInt(String string) {
        return this.configuration.getInt(string);
    }

    public int getInt(String string, int n) {
        return this.configuration.getInt(string, n);
    }

    public String getString(String string) {
        return this.configuration.getString(string);
    }

    public String getString(String string, String string2) {
        return this.configuration.getString(string, string2);
    }

    public double getDouble(String string) {
        return this.configuration.getDouble(string);
    }

    public double getDouble(String string, double d) {
        return this.configuration.getDouble(string, d);
    }

    public boolean getBoolean(String string) {
        return this.configuration.getBoolean(string);
    }

    public boolean getBoolean(String string, boolean bl) {
        return this.configuration.getBoolean(string, bl);
    }

    public long getLong(String string) {
        return this.configuration.getLong(string);
    }

    public long getLong(String string, long l) {
        return this.configuration.getLong(string, l);
    }

    public List iC(String string) {
        return this.configuration.getList(string);
    }

    public List getList(String string, List list) {
        return this.configuration.getList(string, list);
    }

    public List iE(String string) {
        return this.configuration.getStringList(string);
    }

    public ConfigurationSection getSection(String string) {
        return this.configuration.getConfigurationSection(string);
    }
}

