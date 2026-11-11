/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;

final class qu
extends Record {
    private final String I;
    private final String i;
    private final List<String> L;

    qu(String string, String string2, List<String> list) {
        this.I = string;
        this.i = string2;
        this.L = list;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{qu.class, "I;i;L", "I", "i", "L"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{qu.class, "I;i;L", "I", "i", "L"}, this);
    }

    @Override
    public final boolean equals(Object object) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{qu.class, "I;i;L", "I", "i", "L"}, this, object);
    }

    public String qA() {
        return this.I;
    }

    public String qw() {
        return this.i;
    }

    public List<String> q3() {
        return this.L;
    }
}

