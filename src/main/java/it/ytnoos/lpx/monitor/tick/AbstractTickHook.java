/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.monitor.tick;

import it.ytnoos.lpx.monitor.tick.TickHook;
import it.ytnoos.lpx.we;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class AbstractTickHook
implements TickHook {
    private final Set<TickHook.Callback> tasks = new CopyOnWriteArraySet<TickHook.Callback>();
    private int tick = 0;

    protected void dR() {
        for (TickHook.Callback callback : this.tasks) {
            callback.onTick(this.tick);
        }
        ++this.tick;
    }

    @Override
    public int getCurrentTick() {
        return this.tick;
    }

    @Override
    public void j9(Object object) {
        this.tasks.add((TickHook.Callback)object);
    }

    @Override
    public void jf(Object object) {
        this.tasks.remove(object);
    }

    private static we b(we we2) {
        return we2;
    }
}

