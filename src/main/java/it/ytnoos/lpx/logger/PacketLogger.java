/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 */
package it.ytnoos.lpx.logger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.config.logger.OutputType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class PacketLogger {
    private static final int MAX_QUEUE = 1000;
    private final LPXBukkit lpxBukkit;
    private final ExecutorService executor;

    public PacketLogger(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("lpx-packet-logger-%d").setDaemon(true).build();
        this.executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1000), threadFactory, new ThreadPoolExecutor.DiscardPolicy());
    }

    public void logPacket(ReceivedPacket receivedPacket) {
        this.logMessage(() -> {
            String string = String.valueOf(receivedPacket);
            long l = receivedPacket.getSize();
            String string2 = receivedPacket.getFormattedElapsedTime();
            String string3 = receivedPacket.getPacketType().getName();
            String string4 = receivedPacket.getClientVersionName();
            return receivedPacket.getPlayerName() + " [" + string4 + "] sent " + string3 + " (" + string2 + "ms) (" + l + " bytes) " + string;
        });
    }

    public void logMessage(Supplier supplier) {
        if (this.executor.isShutdown()) {
            return;
        }
        try {
            OutputType outputType = this.lpxBukkit.getConfigManager().getLoggerConfig().getOutputType();
            if (outputType == OutputType.CONSOLE || outputType == OutputType.BOTH) {
                this.executor.execute(() -> this.lambda$logMessage$1((Supplier)supplier));
            }
            if (outputType == OutputType.FILE || outputType == OutputType.BOTH) {
                this.lpxBukkit.getAlertManager().getLogWriter().writeString(supplier);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {}
    }

    public void shutdown() {
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(5L, TimeUnit.SECONDS)) {
                this.executor.shutdownNow();
            }
        }
        catch (InterruptedException interruptedException) {
            this.executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private /* synthetic */ void lambda$logMessage$1(Supplier supplier) {
        this.lpxBukkit.getPlugin().getLogger().info(supplier);
    }
}

