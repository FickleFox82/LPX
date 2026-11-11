/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  io.netty.channel.Channel
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package it.ytnoos.lpx.watchdog;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.Channel;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.logger.PacketLogger;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class NettyWatchdog
implements Listener {
    private final LPXBukkit lpxBukkit;
    private final ConfigManager configManager;
    private final Map<Long, NettyThread> timings = new HashMap<Long, NettyThread>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ScheduledExecutorService watchdog;

    public NettyWatchdog(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        this.configManager = lPXBukkit.getConfigManager();
    }

    public synchronized void uG() {
        if (this.running.get()) {
            this.uZ();
        } else {
            this.enable();
        }
    }

    private void enable() {
        this.watchdog = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("lpx-netty-watchdog-%d").setDaemon(true).build());
        this.watchdog.scheduleAtFixedRate(() -> {
            if (!this.running.get()) {
                return;
            }
            long l = System.currentTimeMillis();
            for (Map.Entry<Long, NettyThread> entry : this.timings.entrySet()) {
                StackTraceElement[] stackTraceElementArray;
                NettyThread nettyThread = entry.getValue();
                long l2 = l - nettyThread.P;
                long l3 = l - nettyThread.s;
                if (l2 <= this.configManager.getLoggerConfig().getWatchdogConfig().getMaxTime() || l3 <= this.configManager.getLoggerConfig().getWatchdogConfig().getReportCooldown()) continue;
                PacketLogger packetLogger = this.lpxBukkit.getPacketLogger();
                ThreadInfo threadInfo = ManagementFactory.getThreadMXBean().getThreadInfo(new long[]{entry.getKey()}, true, true)[0];
                for (StackTraceElement stackTraceElement : stackTraceElementArray = threadInfo.getStackTrace()) {
                    if (!stackTraceElement.toString().contains("loadClass")) continue;
                    return;
                }
                packetLogger.logMessage(() -> {
                    long l2 = this.configManager.getLoggerConfig().getWatchdogConfig().getMaxTime();
                    return "Netty thread has stopped responding for more than " + l2 + "ms! (Delay: " + l2 + "ms)";
                });
                packetLogger.logMessage(() -> {
                    String string = String.valueOf((Object)threadInfo.getThreadState());
                    boolean bl = threadInfo.isInNative();
                    boolean bl2 = threadInfo.isSuspended();
                    long l = threadInfo.getThreadId();
                    String string2 = threadInfo.getThreadName();
                    return "\tName: " + string2 + " | PID: " + l + " | Suspended: " + bl2 + " | Native: " + bl + " | State: " + string;
                });
                packetLogger.logMessage(() -> "------------------------------");
                packetLogger.logMessage(() -> "Thread Information");
                this.dumpThread(threadInfo, stackTraceElementArray, packetLogger);
                packetLogger.logMessage(() -> "------------------------------");
                nettyThread.s = l;
            }
        }, this.configManager.getLoggerConfig().getWatchdogConfig().getCheckFrequency(), this.configManager.getLoggerConfig().getWatchdogConfig().getCheckFrequency(), TimeUnit.MILLISECONDS);
        this.lpxBukkit.getPlugin().getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.lpxBukkit.getPlugin());
        this.running.set(true);
    }

    public synchronized void uZ() {
        if (!this.running.get()) {
            return;
        }
        HandlerList.unregisterAll((Listener)this);
        this.watchdog.shutdown();
        try {
            if (!this.watchdog.awaitTermination(10L, TimeUnit.SECONDS)) {
                this.watchdog.shutdownNow();
            }
        }
        catch (InterruptedException interruptedException) {
            this.watchdog.shutdownNow();
            Thread.currentThread().interrupt();
        }
        finally {
            this.running.set(false);
        }
    }

    public boolean isRunning() {
        return this.running.get();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.watchPlayer(playerJoinEvent.getPlayer());
    }

    private void watchPlayer(Player player) {
        User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
        if (user == null) {
            return;
        }
        Object object = user.getChannel();
        if (object == null) {
            return;
        }
        if (!player.hasMetadata("fake")) {
            this.watchChannel((Channel)object);
        }
    }

    public void watchChannel(Channel channel) {
        channel.eventLoop().submit(() -> {
            String string = Thread.currentThread().getName();
            long l = Thread.currentThread().getId();
            this.watchdog.execute(() -> this.timings.computeIfAbsent(l, l -> {
                NettyThread nettyThread = new NettyThread(string);
                nettyThread.P = System.currentTimeMillis();
                channel.eventLoop().scheduleAtFixedRate(() -> {
                    nettyThread.P = System.currentTimeMillis();
                }, 1L, 1L, TimeUnit.MILLISECONDS);
                return nettyThread;
            }));
        });
    }

    private void dumpThread(ThreadInfo threadInfo, StackTraceElement[] stackTraceElementArray, PacketLogger packetLogger) {
        StackTraceElement[] stackTraceElementArray2 = stackTraceElementArray;
        if (threadInfo.getLockedMonitors().length != 0) {
            packetLogger.logMessage(() -> "\tThread is waiting on monitor(s):");
            for (MonitorInfo object : threadInfo.getLockedMonitors()) {
                packetLogger.logMessage(() -> "\t\tLocked on: " + String.valueOf(object.getLockedStackFrame()));
            }
        }
        if (threadInfo.getLockedSynchronizers().length != 0) {
            packetLogger.logMessage(() -> "\tThread is waiting on lock(s):");
            for (LockInfo lockInfo : threadInfo.getLockedSynchronizers()) {
                packetLogger.logMessage(() -> "\t\tLocked on: " + lockInfo.toString());
            }
        }
        packetLogger.logMessage(() -> "\tStack:");
        for (StackTraceElement stackTraceElement : stackTraceElementArray2) {
            packetLogger.logMessage(() -> "\t\t" + String.valueOf(stackTraceElement));
        }
    }

    public static class NettyThread {
        private final String name;
        private volatile long P;
        private volatile long s;

        public NettyThread(String string) {
            this.name = string;
        }

        public long UA() {
            return this.P;
        }

        public void TS(long l) {
            this.P = l;
        }

        public long Uz() {
            return this.s;
        }

        public void TR(long l) {
            this.s = l;
        }

        public String getName() {
            return this.name;
        }
    }
}

