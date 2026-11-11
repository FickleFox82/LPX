/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check.abstraction;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;

public interface Check<T> {
    public CheckType getType();

    public CheckResult handle(T var1);

    public PersonalCheckOptions getOptions();

    default public void reload() {
        this.reset();
        this.loadOptions();
    }

    default public boolean isPreVia() {
        return false;
    }

    public void reset();

    public void loadOptions();
}

