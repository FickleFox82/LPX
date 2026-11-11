/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check;

public class FlagInfo {
    private final String name;
    private final Object value;

    public FlagInfo(String string, Object object) {
        this.name = string;
        this.value = object;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public static FlagInfo of(String string, Object object) {
        return new FlagInfo(string, object);
    }

    public String toString() {
        return this.name + "=" + String.valueOf(this.value);
    }
}

