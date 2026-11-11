/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import io.netty.channel.Channel;
import it.ytnoos.lpx.alert.AlertManager;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import it.ytnoos.lpx.check.BukkitCheckManager;
import it.ytnoos.lpx.check.PendingPunishment;
import it.ytnoos.lpx.commands.LPXCommand;
import it.ytnoos.lpx.common.utils.NettyUtils;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.inventories.InventoryContainer;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.logger.PacketLogger;
import it.ytnoos.lpx.monitor.PacketTickMonitor;
import it.ytnoos.lpx.player.PlayerRegistryImpl;
import it.ytnoos.lpx.plugin.LPXBukkitPlugin;
import it.ytnoos.lpx.qv;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.watchdog.NettyWatchdog;
import it.ytnoos.lpx.wv;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;
import java.util.logging.Level;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public class LPXBukkit
implements LPX {
    private final LPXBukkitPlugin lpxBukkitPlugin;
    private final PlayerRegistry registry;
    private final CheckManager checkManager;
    private final ConfigManager configManager;
    private final InventoryManager inventoryManager;
    private final InventoryContainer inventoryContainer;
    private final PacketLogger packetLogger;
    private final AlertManager alertManager;
    private final ArrayDeque<PendingPunishment> globalViolations;
    private final PacketTickMonitor packetTickMonitor;
    private final NettyWatchdog nettyWatchdog;
    private boolean k = true;
    private boolean Y = false;
    private long lastTick;
    @Nullable
    private LPXCommand lpxCommand;

    public LPXBukkit(LPXBukkitPlugin lPXBukkitPlugin) {
        this.lpxBukkitPlugin = lPXBukkitPlugin;
        this.globalViolations = new ArrayDeque();
        this.configManager = new ConfigManager(lPXBukkitPlugin);
        this.alertManager = new AlertManager(this);
        this.registry = new PlayerRegistryImpl(this);
        this.inventoryManager = new InventoryManager(this);
        this.inventoryContainer = new InventoryContainer(this);
        this.packetLogger = new PacketLogger(this);
        this.checkManager = new BukkitCheckManager(this);
        this.packetTickMonitor = new PacketTickMonitor(this);
        this.nettyWatchdog = new NettyWatchdog(this);
        PacketEvents.getAPI().init();
        try {
            Class<?> clazz = Class.forName("it.ytnoos.lpx.z");
            Method method = clazz.getDeclaredMethod("R\u04a1", new Class[0]);
            method.invoke(clazz.getDeclaredConstructor(LPX.class).newInstance(this), new Object[0]);
        }
        catch (Exception exception) {
            this.getLogger().log(Level.SEVERE, exception, () -> "Unable to verify your license, make sure you have put your license key in the config.yml file and read #license-not-working on our discord.");
            this.disable(false);
        }
    }

    @Override
    public void disable(boolean bl) {
        if (this.Y) {
            return;
        }
        this.Y = true;
        if (!bl) {
            this.lpxBukkitPlugin.getServer().getPluginManager().disablePlugin((Plugin)this.lpxBukkitPlugin);
        }
        if (this.registry != null) {
            this.registry.uninjectAll();
        }
        if (this.packetLogger != null) {
            this.packetLogger.shutdown();
        }
        if (this.nettyWatchdog != null) {
            this.nettyWatchdog.uZ();
        }
        this.packetTickMonitor.pj();
        PacketEvents.getAPI().terminate();
        this.alertManager.tI();
    }

    @Override
    public void reloadConfig() {
        this.configManager.Js(this);
        if (this.lpxCommand != null) {
            this.lpxCommand._v();
            if (PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_13)) {
                for (Player player : this.lpxBukkitPlugin.getServer().getOnlinePlayers()) {
                    FoliaScheduler.getEntityScheduler().run((Entity)player, this.lpxBukkitPlugin, object -> player.updateCommands(), null);
                }
            }
        }
    }

    public LPXCommand getLpxCommand() {
        return this.lpxCommand;
    }

    public void setLpxCommand(LPXCommand lPXCommand) {
        this.lpxCommand = lPXCommand;
    }

    @Override
    public InventoryViewAdapter getInventoryViewAdapter(Player player) {
        if (PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_21)) {
            return new qv(player);
        }
        return new wv(player);
    }

    public void registerListener(Listener listener) {
        this.lpxBukkitPlugin.getServer().getPluginManager().registerEvents(listener, (Plugin)this.lpxBukkitPlugin);
    }

    public void registerListeners(Listener[] listenerArray) {
        Listener[] listenerArray2;
        for (Listener listener : listenerArray2 = listenerArray) {
            this.registerListener(listener);
        }
    }

    @Override
    public String getServer() {
        return this.configManager.getOptionsConfig().getServer();
    }

    @Override
    public LPXPlugin getPlugin() {
        return this.lpxBukkitPlugin;
    }

    @Override
    public PlayerRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public CheckManager getCheckManager() {
        return this.checkManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public AlertManager getAlertManager() {
        return this.alertManager;
    }

    public PacketLogger getPacketLogger() {
        return this.packetLogger;
    }

    @Override
    public boolean isUpdated() {
        return this.k;
    }

    @Override
    public void setUpdated(boolean bl) {
        this.k = bl;
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public InventoryContainer getInventoryContainer() {
        return this.inventoryContainer;
    }

    public Deque getRecentViolations() {
        return this.globalViolations;
    }

    public PacketTickMonitor getPacketTickMonitor() {
        return this.packetTickMonitor;
    }

    public NettyWatchdog getNettyWatchdog() {
        return this.nettyWatchdog;
    }

    @Override
    public long getFirstJoinTime() {
        return this.lastTick;
    }

    @Override
    public void setFirstJoinTime(long l) {
        this.lastTick = l;
    }

    @Override
    public boolean canPacketLogHeavy() {
        return this.lastTick != 0L && System.currentTimeMillis() - this.lastTick > 5000L;
    }

    public void kick(Player player, String string) {
        this.registry.getPlayer(player).ifPresent(protocolPlayer -> protocolPlayer.kick(string));
    }

    public void forceKick(ProtocolPlayer protocolPlayer, String string) {
        User user = protocolPlayer.getUser();
        protocolPlayer.getPlayer().ifPresent(player -> PacketEvents.getAPI().getPlayerManager().sendPacket(player, new WrapperPlayServerDisconnect(MessageFormatter.deserialize(string))));
        try {
            if (user != null) {
                NettyUtils.closeChannel((Channel)user.getChannel());
            }
        }
        catch (Exception exception) {
            this.getLogger().log(Level.SEVERE, exception, () -> "Unable to force kick player " + user.getName());
        }
    }

    public void debug(Supplier supplier) {
        if (this.configManager.getOptionsConfig().isDebug()) {
            this.getLogger().info(supplier);
        }
    }
}

