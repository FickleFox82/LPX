/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerShearEntityEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.player.PrinterStatus;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener
implements Listener {
    private final LPXBukkit lpxBukkit;

    public PlayerListener(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        block2: {
            Player player = playerJoinEvent.getPlayer();
            if (this.lpxBukkit.getFirstJoinTime() == 0L) {
                this.lpxBukkit.setFirstJoinTime(System.currentTimeMillis());
            }
            if (this.lpxBukkit.getConfigManager().getOptionsConfig().isCheckUpdates() && !this.lpxBukkit.isUpdated() && player.hasPermission("lpx.update")) {
                MessageFormatter.send(player, this.lpxBukkit.getConfigManager().getMessagesConfig().getUpdateFound());
            }
            if (this.lpxBukkit.getConfigManager().getOptionsConfig().isAlertsOnJoin()) break block2;
            this.lpxBukkit.getAlertManager().toggleAlerts(player);
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if (!this.lpxBukkit.getConfigManager().getOptionsConfig().getPrinterConfig().isAutomatic()) {
            return;
        }
        ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(blockPlaceEvent.getPlayer()).orElse(null);
        if (protocolPlayer == null) {
            return;
        }
        PrinterData printerData = protocolPlayer.getPrinterData();
        printerData.updateLastPlace();
        if (!printerData.isUsingPrinter() && printerData.increaseBlocksPlaced() >= this.lpxBukkit.getConfigManager().getOptionsConfig().getPrinterConfig().getPlaceThreshold()) {
            printerData.setPrinterMode(PrinterStatus.AUTOMATIC);
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void commandExecute(PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        block1: {
            ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(playerCommandPreprocessEvent.getPlayer()).orElse(null);
            if (protocolPlayer == null || protocolPlayer.ignoreChecks()) {
                return;
            }
            if (protocolPlayer.handleChecks(playerCommandPreprocessEvent.getClass(), playerCommandPreprocessEvent).result() != CheckResult.DENY) break block1;
            playerCommandPreprocessEvent.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void portalTeleport(PlayerTeleportEvent playerTeleportEvent) {
        if (!this.lpxBukkit.getConfigManager().getOptionsConfig().T9()) {
            return;
        }
        PlayerTeleportEvent.TeleportCause teleportCause = playerTeleportEvent.getCause();
        if (teleportCause != PlayerTeleportEvent.TeleportCause.END_PORTAL && teleportCause != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && !teleportCause.name().equals("END_GATEWAY")) {
            return;
        }
        ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(playerTeleportEvent.getPlayer()).orElse(null);
        if (protocolPlayer == null || protocolPlayer.ignoreChecks()) {
            return;
        }
        if (System.currentTimeMillis() - protocolPlayer.getLastPortalTeleport() <= (long)this.lpxBukkit.getConfigManager().getOptionsConfig().getNetherPortalDelay()) {
            playerTeleportEvent.setCancelled(true);
        } else {
            protocolPlayer.updateLastPortalTeleport();
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void teleportEvent(PlayerTeleportEvent playerTeleportEvent) {
        this.lpxBukkit.getRegistry().getPlayer(playerTeleportEvent.getPlayer()).ifPresent(ProtocolPlayer::updateLastTeleport);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void respawnEvent(PlayerRespawnEvent playerRespawnEvent) {
        this.lpxBukkit.getRegistry().getPlayer(playerRespawnEvent.getPlayer()).ifPresent(ProtocolPlayer::updateLastTeleport);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void projectileLaunch(ProjectileLaunchEvent projectileLaunchEvent) {
        block1: {
            if (!this.lpxBukkit.getConfigManager().getOptionsConfig().TW()) {
                return;
            }
            Projectile projectile = projectileLaunchEvent.getEntity();
            if (!(projectile instanceof Arrow) || !(projectile.getVelocity().lengthSquared() > (double)this.lpxBukkit.getConfigManager().getOptionsConfig().getMaxArrowVelocity())) break block1;
            projectileLaunchEvent.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void shearEntity(PlayerShearEntityEvent playerShearEntityEvent) {
        if (!this.lpxBukkit.getConfigManager().getOptionsConfig().Tu()) {
            return;
        }
        ProtocolPlayer protocolPlayer = this.lpxBukkit.getRegistry().getPlayer(playerShearEntityEvent.getPlayer()).orElse(null);
        if (protocolPlayer == null || protocolPlayer.ignoreChecks()) {
            return;
        }
        if (System.currentTimeMillis() - protocolPlayer.getLastShears() <= (long)this.lpxBukkit.getConfigManager().getOptionsConfig().getShearsCooldown()) {
            playerShearEntityEvent.setCancelled(true);
        } else {
            protocolPlayer.updateLastShears();
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void respawnEvent(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        if (this.lpxBukkit.getConfigManager().getOptionsConfig().isBreakCloseInventory() && player.getOpenInventory() != null) {
            blockBreakEvent.getPlayer().closeInventory();
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

