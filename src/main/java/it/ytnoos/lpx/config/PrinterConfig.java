/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import it.ytnoos.lpx.we;

public class PrinterConfig
extends ConfigSection {
    private static final String t = "printer";
    private final boolean automatic = this.getBoolean("automatic");
    private final int placeThreshold = this.getInt("place-threshold");
    private final int disableDelay = this.getInt("disable-delay");
    private final boolean alerts = this.getBoolean("alerts");
    private final String permission = this.eu("permission");
    private final String join = this.eu("join");
    private final String leave = this.eu("leave");

    public PrinterConfig(LPXPlugin lPXPlugin, String string, MessagesConfig messagesConfig) {
        super(lPXPlugin, string + ".printer", messagesConfig);
    }

    public boolean isAutomatic() {
        return this.automatic;
    }

    public int getPlaceThreshold() {
        return this.placeThreshold;
    }

    public int getDisableDelay() {
        return this.disableDelay;
    }

    public boolean isAlerts() {
        return this.alerts;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getJoin() {
        return this.join;
    }

    public String getLeave() {
        return this.leave;
    }

    private static we a(we we2) {
        return we2;
    }
}

