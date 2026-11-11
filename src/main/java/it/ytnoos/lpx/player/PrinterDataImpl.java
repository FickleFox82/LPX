/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.player;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alert.AlertManager;
import it.ytnoos.lpx.api.player.PrinterData;
import it.ytnoos.lpx.api.player.PrinterStatus;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.utils.TimeUtils;
import it.ytnoos.lpx.config.PrinterConfig;
import it.ytnoos.lpx.we;

public class PrinterDataImpl
implements PrinterData {
    private final LPXBukkit lpxBukkit;
    private final ProtocolPlayer player;
    private PrinterStatus printerStatus = PrinterStatus.NONE;
    private long Y = TimeUtils.current();
    private long t = TimeUtils.current();
    private int blocksPlaced;
    private static final String MODE_PLACEHOLDER = "%mode%";

    public PrinterDataImpl(LPXBukkit lPXBukkit, ProtocolPlayer protocolPlayer) {
        this.lpxBukkit = lPXBukkit;
        this.player = protocolPlayer;
    }

    @Override
    public PrinterStatus getPrinterStatus() {
        return this.printerStatus;
    }

    @Override
    public boolean isUsingPrinter() {
        return this.printerStatus != PrinterStatus.NONE;
    }

    @Override
    public void setPrinterMode(boolean bl) {
        this.setPrinterMode(bl ? PrinterStatus.API : PrinterStatus.NONE);
    }

    @Override
    public void setPrinterMode(PrinterStatus printerStatus) {
        this.printerStatus = printerStatus;
        PrinterConfig printerConfig = this.lpxBukkit.getConfigManager().getOptionsConfig().getPrinterConfig();
        if (!printerConfig.isAlerts()) {
            return;
        }
        String string = printerConfig.getPermission();
        AlertManager alertManager = this.lpxBukkit.getAlertManager();
        if (printerStatus != PrinterStatus.NONE) {
            alertManager.sendPermissionAlert(alertManager.replacePlayer(printerConfig.getJoin(), this.player).replace(MODE_PLACEHOLDER, printerStatus.name()), string);
            return;
        }
        alertManager.sendPermissionAlert(alertManager.replacePlayer(printerConfig.getLeave(), this.player), string);
    }

    @Override
    public void updateLastPlace() {
        this.Y = TimeUtils.current();
    }

    @Override
    public long getLastPlace() {
        return this.Y;
    }

    @Override
    public int increaseBlocksPlaced() {
        if (TimeUtils.difference(this.t) > 1000L) {
            this.t = TimeUtils.current();
            this.blocksPlaced = 0;
        }
        return ++this.blocksPlaced;
    }

    @Override
    public int getBlocksPlaced() {
        return this.blocksPlaced;
    }

    private static we a(we we2) {
        return we2;
    }
}

