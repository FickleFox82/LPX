/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check;

import it.ytnoos.lpx.we;

public enum CheckResult {
    ALLOW,
    CHANGE,
    DENY;


    public static CheckResult merge(CheckResult checkResult, CheckResult checkResult2) {
        if (checkResult == DENY || checkResult2 == DENY) {
            return DENY;
        }
        if (checkResult == CHANGE || checkResult2 == CHANGE) {
            return CHANGE;
        }
        return ALLOW;
    }

    public CheckResult merge(CheckResult checkResult) {
        return CheckResult.merge(this, checkResult);
    }

    private static we a(we we2) {
        return we2;
    }
}

