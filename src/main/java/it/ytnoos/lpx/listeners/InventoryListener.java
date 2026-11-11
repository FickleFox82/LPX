/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.InventoryHolder
 */
package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.utils.BlockStateHandler;
import it.ytnoos.lpx.we;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener
implements Listener {
    private final LPXBukkit lpxBukkit;

    public InventoryListener(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void interactContainer(PlayerInteractEvent playerInteractEvent) {
        block3: {
            if (playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK || !(BlockStateHandler.getBlockState(playerInteractEvent.getClickedBlock()) instanceof InventoryHolder) || !this.lpxBukkit.getConfigManager().getOptionsConfig().TJ()) {
                return;
            }
            ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(playerInteractEvent.getPlayer()).orElse(null);
            if (protocolPlayer == null || protocolPlayer.getPrinterData().isUsingPrinter()) {
                return;
            }
            if (TimeUtils.difference(protocolPlayer.getLastContainerInteract()) < this.lpxBukkit.getConfigManager().getOptionsConfig().getInteractContainerDelay()) {
                playerInteractEvent.setUseInteractedBlock(Event.Result.DENY);
                return;
            }
            if (playerInteractEvent.isCancelled()) break block3;
            protocolPlayer.updateLastContainerInteract();
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

