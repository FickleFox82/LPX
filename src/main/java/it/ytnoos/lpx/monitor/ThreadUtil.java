/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.monitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadUtil {
    private static final Pattern VERSION_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");

    private ThreadUtil() {
    }

    public static int extractNumber(String string) {
        Matcher matcher = VERSION_PATTERN.matcher(string);
        int n = -1;
        while (matcher.find()) {
            try {
                n = Integer.parseInt(matcher.group());
            }
            catch (NumberFormatException numberFormatException) {}
        }
        return n;
    }
}

