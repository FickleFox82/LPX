/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package it.ytnoos.lpx.monitor.tick;

import it.ytnoos.lpx.monitor.tick.AbstractTickHook;
import it.ytnoos.lpx.monitor.tick.TickHook;
import it.ytnoos.lpx.we;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BukkitTickHook
extends AbstractTickHook
implements TickHook,
Runnable {
    private final Plugin plugin;
    private BukkitTask task;

    public BukkitTickHook(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.dR();
    }

    @Override
    public void oo() {
        long l = 67667042023807L;
        this.task = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, (Runnable)this, 1L, 1L);
        if (l >= 1L) {
            // empty if block
        }
    }

    @Override
    public void close() {
        this.task.cancel();
    }

    private static we a(we we2) {
        return we2;
    }
}

