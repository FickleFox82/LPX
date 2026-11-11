/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check.options;

import it.ytnoos.lpx.api.check.options.CheckOptions;

public class PersonalCheckOptions {
    private final CheckOptions checkOptions;
    private int vl;
    private double buffer;

    public PersonalCheckOptions(CheckOptions checkOptions) {
        this.checkOptions = checkOptions;
    }

    public CheckOptions getCheckOptions() {
        return this.checkOptions;
    }

    public int getVl() {
        return this.vl;
    }

    public void setVl(int n) {
        this.vl = n;
    }

    public void increaseVl() {
        ++this.vl;
    }

    public double getBuffer() {
        return this.buffer;
    }

    public void setBuffer(double d) {
        this.buffer = d;
    }

    public void reset() {
        this.vl = 0;
        this.buffer = 0.0;
    }
}

