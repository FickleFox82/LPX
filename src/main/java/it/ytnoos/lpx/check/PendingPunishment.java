/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check;

import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;
import it.ytnoos.lpx.we;

public class PendingPunishment {
    private final String name;
    private final PersonalCheckOptions checkOptions;
    private final long timestamp;
    private final FlagInfo[] info;

    public PendingPunishment(String string, PersonalCheckOptions personalCheckOptions, long l, FlagInfo[] flagInfoArray) {
        this.name = string;
        this.checkOptions = personalCheckOptions;
        this.timestamp = l;
        this.info = flagInfoArray;
    }

    public String getName() {
        return this.name;
    }

    public PersonalCheckOptions getOptions() {
        return this.checkOptions;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public FlagInfo[] getInfo() {
        return this.info;
    }

    private static we a(we we2) {
        return we2;
    }
}

