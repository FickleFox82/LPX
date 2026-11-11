/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check;

import it.ytnoos.lpx.api.check.abstraction.CheckFunction;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;

public class RegisteredCheck<T> {
    private final CheckType type;
    private final CheckOptions options;
    private final CheckFunction function;

    public RegisteredCheck(CheckType checkType, CheckOptions checkOptions, CheckFunction checkFunction) {
        this.type = checkType;
        this.options = checkOptions;
        this.function = checkFunction;
    }

    public CheckType getType() {
        return this.type;
    }

    public CheckOptions getOptions() {
        return this.options;
    }

    public CheckFunction getFunction() {
        return this.function;
    }
}

