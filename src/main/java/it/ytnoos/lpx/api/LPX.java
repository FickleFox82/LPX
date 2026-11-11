/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.api;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

public interface LPX {
    public String getServer();

    public LPXPlugin getPlugin();

    public PlayerRegistry getRegistry();

    public CheckManager getCheckManager();

    public void disable(boolean var1);

    public void reloadConfig();

    default public Logger getLogger() {
        return this.getPlugin().getLogger();
    }

    public InventoryViewAdapter getInventoryViewAdapter(Player var1);

    public boolean isUpdated();

    public void setUpdated(boolean var1);

    public long getFirstJoinTime();

    public void setFirstJoinTime(long var1);

    public boolean canPacketLogHeavy();
}

