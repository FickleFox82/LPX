/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check.enums;

import it.ytnoos.lpx.we;

public enum CheckCategory {
    NETTY("netty"),
    WINDOW("window"),
    ITEM("item"),
    CREATIVE("creative"),
    PLACE("place"),
    FLOOD("flood"),
    POSITION("position"),
    PAYLOAD("payload"),
    BOOK("book"),
    TAB("tab"),
    COMMAND("command"),
    RECIPE("recipe");

    private final String name;

    private CheckCategory(String string2) {
        this.name = string2;
    }

    public String toString() {
        return this.name;
    }

    public String getFormattedName() {
        return CheckCategory.capitalise(this.toString());
    }

    private static String capitalise(String string) {
        if (string == null) {
            return null;
        }
        return string.isEmpty() ? "" : Character.toTitleCase(string.charAt(0)) + string.substring(1);
    }

    private static we a(we we2) {
        return we2;
    }
}

