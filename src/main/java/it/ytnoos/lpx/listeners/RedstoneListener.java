/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockRedstoneEvent
 */
package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.we;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RedstoneListener
implements Listener {
    private final LPXBukkit lpxBukkit;
    private final BlockFace[] faces = BlockFace.values();

    public RedstoneListener(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @EventHandler
    public void onRedstoneEvent(BlockRedstoneEvent blockRedstoneEvent) {
        block0: {
            Block block;
            String string;
            if (!this.lpxBukkit.getConfigManager().getOptionsConfig().isTrapdoorRailRedstone() || !(string = (block = blockRedstoneEvent.getBlock()).getType().name()).contains("TRAP") || !string.contains("DOOR") || !this.isNearRail(block)) break block0;
            blockRedstoneEvent.setNewCurrent(blockRedstoneEvent.getOldCurrent());
        }
    }

    private boolean isNearRail(Block block) {
        for (BlockFace blockFace : this.faces) {
            if (!block.getRelative(blockFace).getType().name().contains("RAIL")) continue;
            return true;
        }
        return false;
    }

    private static we a(we we2) {
        return we2;
    }
}

