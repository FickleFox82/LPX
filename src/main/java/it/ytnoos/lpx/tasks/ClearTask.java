/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package it.ytnoos.lpx.tasks;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.we;
import org.bukkit.scheduler.BukkitRunnable;

public class ClearTask
extends BukkitRunnable {
    private final LPXBukkit lpxBukkit;

    public ClearTask(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    public void run() {
        for (ProtocolPlayer protocolPlayer : this.lpxBukkit.getRegistry().getPlayers().values()) {
            for (CheckContainer<?> checkContainer : protocolPlayer.getChecks().values()) {
                for (Check check : checkContainer) {
                    check.getOptions().setVl(0);
                    check.getOptions().setBuffer(0.0);
                }
            }
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

