/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.monitor.tick;

public interface TickHook
extends AutoCloseable {
    public void oo();

    @Override
    public void close();

    public int getCurrentTick();

    public void j9(Object var1);

    public void jf(Object var1);

    public static interface Callback {
        public void onTick(int var1);
    }
}

