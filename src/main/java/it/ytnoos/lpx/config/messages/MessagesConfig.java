/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config.messages;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.AlertsConfig;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.we;

public class MessagesConfig
extends ConfigSection {
    private static final String m = "messages";
    private final String mainColor = this.eu("main-color");
    private final String secondColor = this.eu("second-color");
    private final String prefix;
    private final String kick;
    private final String kickAlert;
    private final String noPermission;
    private final String invalidArguments;
    private final String injectionFailure;
    private final String updateFound;
    private final String nullAddress;
    private final String unknownCommand;
    private final AlertsConfig alertsConfig;

    public MessagesConfig(LPXPlugin lPXPlugin) {
        super(lPXPlugin, "messages", null);
        this.setMessagesConfig(this);
        this.prefix = this.eu("prefix");
        this.kick = this.eu("kick");
        this.kickAlert = this.eu("kick-alert");
        this.noPermission = this.eu("no-permission");
        this.invalidArguments = this.eu("invalid-arguments");
        this.injectionFailure = this.eu("injection-failure");
        this.updateFound = this.eu("update-found");
        this.nullAddress = this.eu("null-address");
        this.unknownCommand = this.eu("unknown-command");
        this.alertsConfig = new AlertsConfig(lPXPlugin, this, "messages");
    }

    public String getMainColor() {
        return this.mainColor;
    }

    public String getSecondColor() {
        return this.secondColor;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getKick() {
        return this.kick;
    }

    public String getKickAlert() {
        return this.kickAlert;
    }

    public String getNoPermission() {
        return this.noPermission;
    }

    public String getInvalidArguments() {
        return this.invalidArguments;
    }

    public String getInjectionFailure() {
        return this.injectionFailure;
    }

    public String getUpdateFound() {
        return this.updateFound;
    }

    public String getNullAddress() {
        return this.nullAddress;
    }

    public String getUnknownCommand() {
        return this.unknownCommand;
    }

    public AlertsConfig getAlertsConfig() {
        return this.alertsConfig;
    }

    private static we a(we we2) {
        return we2;
    }
}

