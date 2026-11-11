/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.destroystokyo.paper.event.server.ServerTickStartEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package it.ytnoos.lpx.monitor.tick;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import it.ytnoos.lpx.monitor.tick.AbstractTickHook;
import it.ytnoos.lpx.monitor.tick.TickHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PaperTickHook
extends AbstractTickHook
implements TickHook,
Listener {
    private final Plugin plugin;

    public PaperTickHook(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerTickEvent(ServerTickStartEvent serverTickStartEvent) {
        this.dR();
    }

    @Override
    public void oo() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this, this.plugin);
    }

    @Override
    public void close() {
        HandlerList.unregisterAll((Listener)this);
    }
}

