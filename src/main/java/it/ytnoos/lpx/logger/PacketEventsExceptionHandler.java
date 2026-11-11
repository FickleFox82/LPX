/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.logger;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.we;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketEventsExceptionHandler
implements BiConsumer<String, Throwable> {
    private static final long REPORT_COOLDOWN = 10000L;
    private static final Pattern PLAYER_PATTERN = Pattern.compile("(?:from|to) ([^ ]+) \\(");
    private static final String UNKNOWN_PLAYER = "an unknown player";
    private final LPXBukkit lpxBukkit;
    private final Map<String, Long> lastReports = new ConcurrentHashMap<String, Long>();

    public PacketEventsExceptionHandler(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @Override
    public void accept(String string, Throwable throwable) {
        boolean bl = this.lpxBukkit.getConfigManager().getOptionsConfig().isKickOnException();
        this.lpxBukkit.getAlertManager().getLogWriter().execute(() -> this.handleAsync(string, throwable, bl));
    }

    private void handleAsync(String string, Throwable throwable, boolean bl) {
        long l;
        String string2 = PacketEventsExceptionHandler.buildMessage(string, throwable, bl);
        if (bl) {
            this.lpxBukkit.getLogger().warning(string2);
            this.lpxBukkit.getAlertManager().getLogWriter().YD(() -> "[PacketEvents] " + string2, throwable);
            return;
        }
        String string3 = PacketEventsExceptionHandler.tw(string);
        if (!PacketEventsExceptionHandler.shouldLog(this.lastReports, string3, l = System.currentTimeMillis())) {
            return;
        }
        this.lpxBukkit.getLogger().warning(string2);
        this.lpxBukkit.getAlertManager().getLogWriter().YD(() -> "[PacketEvents] " + string2, throwable);
    }

    static String buildMessage(String string, Throwable throwable, boolean bl) {
        String string2 = PacketEventsExceptionHandler.tR(string);
        String string3 = PacketEventsExceptionHandler.extractReason(string, throwable);
        if (bl) {
            return "LPX blocked an invalid packet from " + string2 + " and disconnected the player for safety. Reason: " + string3 + ". Full stacktrace saved in LPX logs.";
        }
        return "LPX blocked an invalid packet from " + string2 + ". The player was not disconnected because kick-on-exception is disabled. Reason: " + string3 + ". Full stacktrace saved in LPX logs; throttling repeated messages for this player for 10 seconds.";
    }

    static String tw(String string) {
        return PacketEventsExceptionHandler.tR(string);
    }

    public static boolean shouldLog(Map map, String string, long l) {
        Long l2 = (Long)map.get(string);
        if (l2 != null && l - l2 < 10000L) {
            return false;
        }
        map.put(string, l);
        return true;
    }

    private static String tR(String string) {
        Matcher matcher = PLAYER_PATTERN.matcher(string);
        return matcher.find() ? matcher.group(1) : UNKNOWN_PLAYER;
    }

    private static String extractReason(String string, Throwable throwable) {
        Object object;
        int n = string.lastIndexOf("): ");
        if (n != -1 && !((String)(object = string.substring(n + 3).trim())).isEmpty()) {
            return object;
        }
        object = throwable;
        while (((Throwable)object).getCause() != null) {
            object = ((Throwable)object).getCause();
        }
        String string2 = ((Throwable)object).getMessage();
        if (string2 != null && !string2.trim().isEmpty()) {
            return string2;
        }
        return object.getClass().getSimpleName();
    }

    private static we a(we we2) {
        return we2;
    }
}

