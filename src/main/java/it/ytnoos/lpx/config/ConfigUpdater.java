/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.f;
import it.ytnoos.lpx.i2;
import it.ytnoos.lpx.qu;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

public class ConfigUpdater {
    static final String u = "config-version";
    private static final String O = "checks.flood.b.options.limits";
    private static final String h = "checks.netty.b";
    private static final Map<String, String> n = Map.of("options.max-item-depth", "checks.netty.b.options.max-item-depth", "options.max-items-per-packet", "checks.netty.b.options.max-items-per-packet");

    public static void update(LPXPlugin lPXPlugin) {
        ArrayList<String> arrayList;
        YamlConfiguration yamlConfiguration;
        InputStream inputStream;
        File file = lPXPlugin.getConfigFile();
        if (file == null || !file.exists()) {
            return;
        }
        try {
            inputStream = lPXPlugin.getResource("config.yml");
            try {
                if (inputStream == null) {
                    return;
                }
                yamlConfiguration = YamlConfiguration.loadConfiguration((Reader)new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            }
            finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        catch (IOException iOException) {
            lPXPlugin.getLogger().warning("Failed to read default config for auto-update");
            return;
        }
        inputStream = YamlConfiguration.loadConfiguration((File)file);
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : n.entrySet()) {
            if (!inputStream.isSet(entry.getKey())) continue;
            linkedHashMap.put(entry.getKey(), inputStream.isSet(entry.getValue()) ? null : inputStream.getString(entry.getKey()));
        }
        boolean bl = inputStream.isList(O);
        boolean bl2 = inputStream.isSet("checks.netty.b.options.resolved");
        int n = inputStream.getInt(u, 0);
        Set set = ConfigUpdater.gM((ConfigurationSection)yamlConfiguration, (ConfigurationSection)inputStream);
        if (bl2) {
            set.removeIf(string -> string.startsWith("checks.netty.b."));
        }
        if (set.isEmpty() && linkedHashMap.isEmpty() && !bl && !bl2 && n >= 2) {
            return;
        }
        List list = ConfigUpdater.y1(lPXPlugin);
        if (list == null) {
            return;
        }
        try {
            arrayList = new ArrayList<String>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        }
        catch (IOException iOException) {
            lPXPlugin.getLogger().warning("Failed to read config file for auto-update");
            return;
        }
        if (bl) {
            ConfigUpdater.Of(arrayList, list, O);
        }
        if (bl2) {
            ConfigUpdater.Of(arrayList, list, h);
        }
        ConfigUpdater.ya(arrayList, list, set, linkedHashMap, lPXPlugin.getLogger());
        List list2 = ConfigUpdater.tC(arrayList, n, lPXPlugin.getLogger());
        try {
            Files.write(file.toPath(), arrayList, StandardCharsets.UTF_8, new OpenOption[0]);
        }
        catch (IOException iOException) {
            lPXPlugin.getLogger().warning("Failed to write updated config file");
            return;
        }
        lPXPlugin.reload();
        if (bl) {
            lPXPlugin.getLogger().info("Migrated FloodB config to new format with default values.");
        }
        if (bl2) {
            lPXPlugin.getLogger().info("Replaced the removed NettyB check config with the new NettyB check.");
        }
        if (!set.isEmpty()) {
            lPXPlugin.getLogger().info("Config updated: added missing keys " + String.valueOf(set));
        }
        if (!linkedHashMap.isEmpty()) {
            lPXPlugin.getLogger().info("Config updated: moved keys " + String.valueOf(linkedHashMap.keySet()));
        }
        if (!list2.isEmpty()) {
            lPXPlugin.getLogger().info("Config updated: new default values for " + String.valueOf(list2));
        }
    }

    static List tC(List list, int n, Logger logger) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (f f2 : i2.f) {
            if (f2.JL() <= n) continue;
            for (qu qu2 : f2.Ji()) {
                int n2 = ConfigUpdater.jD(list, qu2.qA());
                if (n2 == -1) continue;
                String string = ConfigUpdater.TQ((String)list.get(n2));
                if (!qu2.q3().contains(string)) continue;
                ConfigUpdater.iS(list, qu2.qA(), qu2.qw());
                arrayList.add(qu2.qA());
                String string2 = qu2.qw();
                String string3 = string;
                String string4 = qu2.qA();
                logger.info("Updating default value of " + string4 + ": " + string3 + " -> " + string2);
            }
        }
        ConfigUpdater.iS(list, u, String.valueOf(2));
        return arrayList;
    }

    private static String TQ(String string) {
        String string2 = string.substring(string.indexOf(58) + 1);
        int n = string2.indexOf(" #");
        if (n != -1) {
            string2 = string2.substring(0, n);
        }
        if ((string2 = string2.trim()).length() >= 2 && (string2.startsWith("\"") && string2.endsWith("\"") || string2.startsWith("'") && string2.endsWith("'"))) {
            string2 = string2.substring(1, string2.length() - 1);
        }
        return string2;
    }

    static void ya(List list, List list2, Set set, /*
     * Issues handling annotations - annotations may be inaccurate
     */
    @Nullable Map map, Logger logger) {
        for (String object : set) {
            if (!ConfigUpdater.OX(list, list2, object)) continue;
            logger.info("Auto updating config: " + object);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() != null) {
                ConfigUpdater.iS(list, n.get(entry.getKey()), (String)entry.getValue());
            }
            ConfigUpdater.jX(list, (String)entry.getKey());
        }
    }

    static void Of(List list, List list2, String string) {
        String string2;
        int n;
        int n2;
        int n3 = ConfigUpdater.jD(list, string);
        if (n3 == -1) {
            return;
        }
        List list3 = ConfigUpdater.j4(list2, string);
        if (list3.isEmpty()) {
            return;
        }
        list3.remove(0);
        int n4 = ((String)list.get(n3)).length() - ((String)list.get(n3)).stripLeading().length();
        for (n2 = n3; n2 > 0 && ((String)list.get(n2 - 1)).stripLeading().startsWith("#") && ((String)list.get(n2 - 1)).length() - ((String)list.get(n2 - 1)).stripLeading().length() == n4; --n2) {
        }
        for (n = n3 + 1; n < list.size() && ((string2 = (String)list.get(n)).trim().isEmpty() || string2.length() - string2.stripLeading().length() > n4 || string2.stripLeading().startsWith("- ")); ++n) {
        }
        while (n > n3 + 1 && ((String)list.get(n - 1)).trim().isEmpty()) {
            --n;
        }
        list.subList(n2, n).clear();
        list.addAll(n2, list3);
    }

    private static int jD(List list, String string) {
        String[] stringArray = string.split("\\.");
        int n = (stringArray.length - 1) * 2;
        String string2 = " ".repeat(n) + stringArray[stringArray.length - 1] + ":";
        for (int i = 0; i < list.size(); ++i) {
            if (!ConfigUpdater.iT((String)list.get(i), string2) || !ConfigUpdater.i8(list, i, stringArray, n)) continue;
            return i;
        }
        return -1;
    }

    private static void iS(List list, String string, String string2) {
        int n = ConfigUpdater.jD(list, string);
        if (n == -1) {
            return;
        }
        String string3 = (String)list.get(n);
        list.set(n, string3.substring(0, string3.indexOf(58) + 1) + " " + string2);
    }

    private static void jX(List list, String string) {
        boolean bl;
        int n;
        int n2 = ConfigUpdater.jD(list, string);
        if (n2 == -1) {
            return;
        }
        for (n = n2; n > 0 && ((String)list.get(n - 1)).stripLeading().startsWith("#"); --n) {
        }
        int n3 = n2 + 1;
        boolean bl2 = n > 0 && ((String)list.get(n - 1)).trim().isEmpty();
        boolean bl3 = bl = n > 0 && ((String)list.get(n - 1)).trim().endsWith(":");
        if ((bl2 || bl) && n3 < list.size() && ((String)list.get(n3)).trim().isEmpty()) {
            ++n3;
        }
        list.subList(n, n3).clear();
    }

    private static Set gM(ConfigurationSection configurationSection, ConfigurationSection configurationSection2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ConfigUpdater.CY(configurationSection, configurationSection2, linkedHashSet);
        return linkedHashSet;
    }

    private static void CY(ConfigurationSection configurationSection, ConfigurationSection configurationSection2, Set set) {
        for (String string : configurationSection.getKeys(false)) {
            String string2;
            String string3 = string2 = configurationSection.getCurrentPath().isEmpty() ? string : configurationSection.getCurrentPath() + "." + string;
            if (!configurationSection2.contains(string)) {
                set.add(string2);
                continue;
            }
            if (!configurationSection.isConfigurationSection(string) || !configurationSection2.isConfigurationSection(string)) continue;
            ConfigUpdater.CY(configurationSection.getConfigurationSection(string), configurationSection2.getConfigurationSection(string), set);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List y1(LPXPlugin lPXPlugin) {
        try (InputStream inputStream = lPXPlugin.getResource("config.yml");){
            if (inputStream == null) {
                List list2 = null;
                return list2;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> list = bufferedReader.lines().toList();
            return list;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private static List j4(List list, String string) {
        int n;
        Object object;
        String string2;
        int n2;
        String[] stringArray = string.split("\\.");
        String string3 = stringArray[stringArray.length - 1];
        int n3 = (stringArray.length - 1) * 2;
        String string4 = " ".repeat(n3) + string3 + ":";
        int n4 = -1;
        for (n2 = 0; n2 < list.size(); ++n2) {
            string2 = (String)list.get(n2);
            if (!ConfigUpdater.iT(string2, string4) || !ConfigUpdater.i8(list, n2, stringArray, n3)) continue;
            n4 = n2;
            break;
        }
        if (n4 == -1) {
            return List.of();
        }
        for (n2 = n4; n2 > 0 && (((String)(object = (string2 = (String)list.get(n2 - 1)).stripLeading())).startsWith("#") || ((String)object).isEmpty()); --n2) {
            n = string2.length() - string2.stripLeading().length();
            if (!((String)object).isEmpty() && n < n3) break;
        }
        while (n2 < n4 && ((String)list.get(n2)).trim().isEmpty()) {
            ++n2;
        }
        int n5 = n4 + 1;
        while (n5 < list.size()) {
            object = (String)list.get(n5);
            if (((String)object).trim().isEmpty()) {
                ++n5;
                continue;
            }
            n = ((String)object).length() - ((String)object).stripLeading().length();
            if (n <= n3) break;
            ++n5;
        }
        while (n5 > n4 + 1 && ((String)list.get(n5 - 1)).trim().isEmpty()) {
            --n5;
        }
        object = new ArrayList();
        object.add("");
        object.addAll(list.subList(n2, n5));
        return object;
    }

    private static boolean iT(String string, String string2) {
        return string.equals(string2) || string.startsWith(string2 + " ") || string.startsWith(string2 + "\n");
    }

    private static boolean i8(List list, int n, String[] stringArray, int n2) {
        String[] stringArray2 = stringArray;
        if (stringArray2.length <= 1) {
            return true;
        }
        for (int i = stringArray2.length - 2; i >= 0; --i) {
            int n3 = i * 2;
            String string = " ".repeat(n3) + stringArray2[i] + ":";
            boolean bl = false;
            for (int j = n - 1; j >= 0; --j) {
                int n4;
                String string2 = (String)list.get(j);
                if (string2.trim().isEmpty() || string2.stripLeading().startsWith("#") || (n4 = string2.length() - string2.stripLeading().length()) > n3) continue;
                if (!ConfigUpdater.iT(string2, string)) break;
                bl = true;
                break;
            }
            if (bl) continue;
            return false;
        }
        return true;
    }

    private static boolean OX(List list, List list2, String string) {
        int n;
        int n2;
        List list3 = ConfigUpdater.j4(list2, string);
        if (list3.isEmpty()) {
            return false;
        }
        list3.remove(0);
        String[] stringArray = string.split("\\.");
        int n3 = (stringArray.length - 1) * 2;
        String string2 = stringArray.length == 1 ? null : string.substring(0, string.lastIndexOf(46));
        for (n2 = ConfigUpdater.jD(list2, string); n2 > 0 && ConfigUpdater._y((String)list2.get(n2 - 1), n3); --n2) {
        }
        boolean bl = n2 > 0 && ((String)list2.get(n2 - 1)).trim().isEmpty();
        int n4 = ConfigUpdater.qT(list, list2, string, n3, string2);
        if (n4 != -1) {
            boolean bl2 = n4 > 0 && ((String)list.get(n4 - 1)).trim().isEmpty();
            ArrayList<String> arrayList = new ArrayList<String>();
            if (bl && !bl2) {
                arrayList.add("");
            }
            arrayList.addAll(list3);
            if (ConfigUpdater.r4(list2, string, n3)) {
                arrayList.add("");
            }
            if (!bl && bl2) {
                list.remove(n4 - 1);
                --n4;
                if (!ConfigUpdater.r4(list2, string, n3)) {
                    arrayList.add("");
                }
            }
            list.addAll(n4, arrayList);
            return true;
        }
        int n5 = n = string2 == null ? list.size() : ConfigUpdater.jQ(list, string2);
        if (n == -1) {
            return false;
        }
        while (n > 0 && ((String)list.get(n - 1)).trim().isEmpty()) {
            --n;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        if (bl) {
            arrayList.add("");
        }
        arrayList.addAll(list3);
        list.addAll(n, arrayList);
        return true;
    }

    private static boolean _y(String string, int n) {
        return string.stripLeading().startsWith("#") && string.length() - string.stripLeading().length() >= n;
    }

    private static int qT(List list, List list2, String string, int n, @Nullable String string2) {
        int n2 = ConfigUpdater.jD(list2, string);
        for (int i = n2 + 1; i < list2.size(); ++i) {
            String string3 = (String)list2.get(i);
            if (string3.trim().isEmpty() || string3.stripLeading().startsWith("#")) continue;
            int n3 = string3.length() - string3.stripLeading().length();
            if (n3 < n) {
                return -1;
            }
            if (n3 > n || string3.stripLeading().startsWith("-")) continue;
            String string4 = string3.trim().substring(0, string3.trim().indexOf(58));
            int n4 = ConfigUpdater.jD(list, (String)(string2 == null ? string4 : string2 + "." + string4));
            if (n4 == -1) continue;
            while (n4 > 0 && ConfigUpdater._y((String)list.get(n4 - 1), n)) {
                --n4;
            }
            return n4;
        }
        return -1;
    }

    private static boolean r4(List list, String string, int n) {
        String string2;
        int n2;
        for (n2 = ConfigUpdater.jD(list, string) + 1; n2 < list.size() && ((string2 = (String)list.get(n2)).trim().isEmpty() || string2.length() - string2.stripLeading().length() > n); ++n2) {
        }
        while (n2 > 0 && ConfigUpdater._y((String)list.get(n2 - 1), n)) {
            --n2;
        }
        return n2 > 0 && ((String)list.get(n2 - 1)).trim().isEmpty();
    }

    private static int jQ(List list, String string) {
        String string2;
        int n;
        int n2 = ConfigUpdater.jD(list, string);
        if (n2 == -1) {
            return -1;
        }
        int n3 = ((String)list.get(n2)).length() - ((String)list.get(n2)).stripLeading().length();
        for (n = n2 + 1; n < list.size() && ((string2 = (String)list.get(n)).trim().isEmpty() || string2.length() - string2.stripLeading().length() > n3); ++n) {
        }
        return n;
    }
}

