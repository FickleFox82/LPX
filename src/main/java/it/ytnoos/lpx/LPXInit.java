/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.ServicePriority
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.manager.server.VersionComparison;
import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.metrics.MetricsLite;
import it.ytnoos.lpx.commands.LPXCommand;
import it.ytnoos.lpx.listeners.CommandSendListener;
import it.ytnoos.lpx.listeners.InventoryListener;
import it.ytnoos.lpx.listeners.LPXListener;
import it.ytnoos.lpx.listeners.PlayerLegacyListener;
import it.ytnoos.lpx.listeners.PlayerListener;
import it.ytnoos.lpx.listeners.PreViaListener;
import it.ytnoos.lpx.listeners.RedstoneListener;
import it.ytnoos.lpx.listeners.ServerPacketListener;
import it.ytnoos.lpx.tasks.ClearTask;
import it.ytnoos.lpx.we;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

public class LPXInit {
    private static boolean called = false;
    private static final String ENABLED_MESSAGE = "LPX enabled!";

    private void enable(LPXBukkit lPXBukkit) {
        if (called) {
            return;
        }
        called = true;
        lPXBukkit.getConfigManager().dX(lPXBukkit.getPlugin());
        lPXBukkit.getAlertManager().tw();
        lPXBukkit.getCheckManager().registerChecks();
        lPXBukkit.getInventoryManager().init();
        lPXBukkit.registerListeners(new Listener[]{new PlayerListener(lPXBukkit), new InventoryListener(lPXBukkit)});
        ServerVersion serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        if (serverVersion.is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_13) && serverVersion.is(VersionComparison.OLDER_THAN, ServerVersion.V_1_19_4)) {
            lPXBukkit.registerListener(new RedstoneListener(lPXBukkit));
        }
        if (serverVersion.is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_13)) {
            lPXBukkit.registerListener(new CommandSendListener(lPXBukkit));
        }
        if (serverVersion.is(VersionComparison.OLDER_THAN_OR_EQUALS, ServerVersion.V_1_8_8)) {
            lPXBukkit.registerListener(new PlayerLegacyListener(lPXBukkit));
        }
        lPXBukkit.setLpxCommand(new LPXCommand(lPXBukkit));
        if (lPXBukkit.getConfigManager().getOptionsConfig().isClearTaskEnabled()) {
            FoliaScheduler.getAsyncScheduler().runAtFixedRate(lPXBukkit.getPlugin(), object -> new ClearTask(lPXBukkit).run(), 1L, (long)lPXBukkit.getConfigManager().getOptionsConfig().getClearTaskDelay() * 20L);
        }
        if (lPXBukkit.getConfigManager().getOptionsConfig().Tn()) {
            new MetricsLite(lPXBukkit.getPlugin());
        }
        lPXBukkit.getLogger().log(Level.INFO, ENABLED_MESSAGE);
        LPXListener lPXListener = new LPXListener(lPXBukkit);
        PacketEvents.getAPI().getEventManager().registerListener(new PreViaListener(lPXListener));
        PacketEvents.getAPI().getEventManager().registerListener(new ServerPacketListener(lPXBukkit, lPXListener));
        Bukkit.getServicesManager().register(LPX.class, (Object)lPXBukkit, (Plugin)lPXBukkit.getPlugin(), ServicePriority.Normal);
    }

    private static we a(we we2) {
        return we2;
    }
}

