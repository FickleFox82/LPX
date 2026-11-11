/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.chat.ComponentSerializer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.utils.string;

import it.ytnoos.lpx.we;
import java.util.HashMap;
import java.util.Map;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class MessageFormatter {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.gson();
    private static final LegacyComponentSerializer legacyComponentSerializer = LegacyComponentSerializer.legacySection();
    private static final PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
    private static final Map<Character, String> LEGACY_COLORS = MessageFormatter.createLegacyMap();

    private MessageFormatter() {
    }

    public static Component deserialize(String string) {
        return miniMessage.deserialize(MessageFormatter.ke(string == null ? "" : string));
    }

    public static String kp(String string) {
        return miniMessage.escapeTags(string == null ? "" : string);
    }

    public static BaseComponent[] toBungee(String string) {
        return ComponentSerializer.parse((String)((String)gsonComponentSerializer.serialize(MessageFormatter.deserialize(string))));
    }

    public static String kB(String string) {
        return plainTextComponentSerializer.serialize(MessageFormatter.deserialize(string));
    }

    public static String k3(String string) {
        return legacyComponentSerializer.serialize(MessageFormatter.deserialize(string));
    }

    public static void send(CommandSender commandSender, String string) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            MessageFormatter.send(player, string);
            return;
        }
        commandSender.sendMessage(MessageFormatter.kB(string));
    }

    public static void send(Player player, String string) {
        player.spigot().sendMessage(MessageFormatter.toBungee(string));
    }

    static String ke(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c;
            String string2;
            char c2 = string.charAt(i);
            if ((c2 == '\u00a7' || c2 == '&') && i + 1 < string.length() && (string2 = LEGACY_COLORS.get(Character.valueOf(c = Character.toLowerCase(string.charAt(i + 1))))) != null) {
                stringBuilder.append(string2);
                ++i;
                continue;
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    private static Map createLegacyMap() {
        HashMap<Character, String> hashMap = new HashMap<Character, String>();
        hashMap.put(Character.valueOf('0'), "<black>");
        hashMap.put(Character.valueOf('1'), "<dark_blue>");
        hashMap.put(Character.valueOf('2'), "<dark_green>");
        hashMap.put(Character.valueOf('3'), "<dark_aqua>");
        hashMap.put(Character.valueOf('4'), "<dark_red>");
        hashMap.put(Character.valueOf('5'), "<dark_purple>");
        hashMap.put(Character.valueOf('6'), "<gold>");
        hashMap.put(Character.valueOf('7'), "<gray>");
        hashMap.put(Character.valueOf('8'), "<dark_gray>");
        hashMap.put(Character.valueOf('9'), "<blue>");
        hashMap.put(Character.valueOf('a'), "<green>");
        hashMap.put(Character.valueOf('b'), "<aqua>");
        hashMap.put(Character.valueOf('c'), "<red>");
        hashMap.put(Character.valueOf('d'), "<light_purple>");
        hashMap.put(Character.valueOf('e'), "<yellow>");
        hashMap.put(Character.valueOf('f'), "<white>");
        hashMap.put(Character.valueOf('k'), "<obfuscated>");
        hashMap.put(Character.valueOf('l'), "<bold>");
        hashMap.put(Character.valueOf('m'), "<strikethrough>");
        hashMap.put(Character.valueOf('n'), "<underlined>");
        hashMap.put(Character.valueOf('o'), "<italic>");
        hashMap.put(Character.valueOf('r'), "<reset>");
        return hashMap;
    }

    private static we a(we we2) {
        return we2;
    }
}

