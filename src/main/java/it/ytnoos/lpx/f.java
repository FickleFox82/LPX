/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import it.ytnoos.lpx.qu;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;

final class f
extends Record {
    private final int U;
    private final List<qu> z;

    f(int n, List<qu> list) {
        this.U = n;
        this.z = list;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{f.class, "U;z", "U", "z"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{f.class, "U;z", "U", "z"}, this);
    }

    @Override
    public final boolean equals(Object object) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{f.class, "U;z", "U", "z"}, this, object);
    }

    public int JL() {
        return this.U;
    }

    public List<qu> Ji() {
        return this.z;
    }
}

