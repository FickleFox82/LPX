/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

public enum DisableDecodingType {
    CREATIVE,
    ALL,
    SMART,
    NONE;

    public static final DisableDecodingType DEFAULT;

    public static DisableDecodingType CD(String string) {
        try {
            return DisableDecodingType.valueOf(string.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return DEFAULT;
        }
    }

    static {
        DEFAULT = SMART;
    }
}

