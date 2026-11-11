/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.player;

import it.ytnoos.lpx.api.player.PrinterStatus;

public interface PrinterData {
    public PrinterStatus getPrinterStatus();

    public boolean isUsingPrinter();

    public void setPrinterMode(boolean var1);

    public void setPrinterMode(PrinterStatus var1);

    public void updateLastPlace();

    public long getLastPlace();

    public int increaseBlocksPlaced();

    public int getBlocksPlaced();
}

