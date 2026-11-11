/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.config.options;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.settings.PacketEventsSettings;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigSection;
import it.ytnoos.lpx.config.ItemStripConfig;
import it.ytnoos.lpx.config.PrinterConfig;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import it.ytnoos.lpx.we;

public class OptionsConfig
extends ConfigSection {
    private static final String i = "options";
    private static final String H = "checks.netty.b.options";
    private final boolean silentFailures = this.getBoolean("silent-failures");
    private final boolean alertsOnJoin;
    private final boolean kickOnException;
    private final boolean hiddenCommand;
    private final boolean bypassPermission;
    private final boolean geyser;
    private final boolean axiom;
    private final boolean checkUpdates;
    private final boolean J;
    private final boolean clearTaskEnabled;
    private final int clearTaskDelay;
    private final PrinterConfig printerConfig;
    private final ItemStripConfig itemStripConfig;
    private final int netherPortalDelay;
    private final boolean r;
    private final int maxArrowVelocity;
    private final boolean q;
    private final int shearsCooldown;
    private final boolean P;
    private final boolean breakCloseInventory;
    private final boolean trapdoorRailRedstone;
    private final boolean B;
    private final long interactContainerDelay;
    private final boolean alertsStore;
    private final int alertsDays;
    private final String server;
    private final int punishDelay;
    private final boolean debug;
    private final boolean o;

    public OptionsConfig(MessagesConfig messagesConfig, LPXPlugin lPXPlugin) {
        super(lPXPlugin, i, messagesConfig);
        PacketEvents.getAPI().getSettings().ignoreFailures(this.silentFailures);
        this.alertsOnJoin = this.getBoolean("alerts-on-join");
        this.kickOnException = this.getBoolean("kick-on-exception");
        PacketEvents.getAPI().getSettings().kickOnPacketException(this.kickOnException);
        this.Tf();
        this.hiddenCommand = this.getBoolean("hidden-command");
        this.bypassPermission = this.getBoolean("bypass-permission");
        this.geyser = this.getBoolean("geyser");
        this.axiom = this.getBoolean("axiom");
        if (this.axiom) {
            PacketEvents.getAPI().getSettings().maxPayloadMultiplier(15);
        }
        this.checkUpdates = this.getBoolean("check-updates");
        this.J = this.getConfig().getBoolean(this.key + ".bstats", true);
        this.clearTaskEnabled = this.getBoolean("clear-task.enabled");
        this.clearTaskDelay = this.getInt("clear-task.delay");
        this.printerConfig = new PrinterConfig(lPXPlugin, i, messagesConfig);
        this.itemStripConfig = new ItemStripConfig(lPXPlugin, i, messagesConfig);
        this.netherPortalDelay = this.getInt("mechanics.nether-portal-delay");
        this.r = this.netherPortalDelay != -1;
        this.maxArrowVelocity = this.getInt("mechanics.max-arrow-velocity");
        this.q = this.maxArrowVelocity != -1;
        this.shearsCooldown = this.getInt("mechanics.shears-cooldown");
        this.P = this.shearsCooldown != -1;
        this.breakCloseInventory = this.getBoolean("mechanics.break-close-inventory");
        this.trapdoorRailRedstone = this.getBoolean("mechanics.trapdoor-rail-redstone");
        this.interactContainerDelay = this.getInt("mechanics.interact-container-delay");
        this.B = this.interactContainerDelay != -1L;
        this.alertsStore = this.getBoolean("alerts.store");
        this.alertsDays = this.getInt("alerts.days");
        this.server = this.eu("server");
        this.punishDelay = this.getInt("punish-delay");
        this.debug = this.getBoolean("debug");
        this.o = this.getConfig().getBoolean(this.key + ".log-cancelled-packets", true);
    }

    private void Tf() {
        PacketEventsSettings packetEventsSettings = PacketEvents.getAPI().getSettings();
        packetEventsSettings.maxItemDepth(this.vp("max-item-depth", 4));
        packetEventsSettings.maxItemsPerPacket(this.vp("max-items-per-packet", 1458));
        packetEventsSettings.maxReceivedNbtElementsPerPacket(this.vp("max-nbt-elements-per-packet", 2500));
        packetEventsSettings.maxReceivedNbtDepth(this.vp("max-nbt-depth", 64));
    }

    private int vp(String string, int n) {
        return this.getConfig().getInt("checks.netty.b.options." + string, n);
    }

    public boolean isSilentFailures() {
        return this.silentFailures;
    }

    public boolean isAlertsOnJoin() {
        return this.alertsOnJoin;
    }

    public boolean isKickOnException() {
        return this.kickOnException;
    }

    public boolean isHiddenCommand() {
        return this.hiddenCommand;
    }

    public boolean isBypassPermission() {
        return this.bypassPermission;
    }

    public boolean isGeyser() {
        return this.geyser;
    }

    public boolean isAxiom() {
        return this.axiom;
    }

    public boolean isCheckUpdates() {
        return this.checkUpdates;
    }

    public boolean Tn() {
        return this.J;
    }

    public boolean isClearTaskEnabled() {
        return this.clearTaskEnabled;
    }

    public int getClearTaskDelay() {
        return this.clearTaskDelay;
    }

    public PrinterConfig getPrinterConfig() {
        return this.printerConfig;
    }

    public ItemStripConfig getItemStripConfig() {
        return this.itemStripConfig;
    }

    public int getNetherPortalDelay() {
        return this.netherPortalDelay;
    }

    public boolean T9() {
        return this.r;
    }

    public int getMaxArrowVelocity() {
        return this.maxArrowVelocity;
    }

    public boolean TW() {
        return this.q;
    }

    public int getShearsCooldown() {
        return this.shearsCooldown;
    }

    public boolean Tu() {
        return this.P;
    }

    public boolean isBreakCloseInventory() {
        return this.breakCloseInventory;
    }

    public boolean isTrapdoorRailRedstone() {
        return this.trapdoorRailRedstone;
    }

    public boolean TJ() {
        return this.B;
    }

    public long getInteractContainerDelay() {
        return this.interactContainerDelay;
    }

    public boolean isAlertsStore() {
        return this.alertsStore;
    }

    public int getAlertsDays() {
        return this.alertsDays;
    }

    public String getServer() {
        return this.server;
    }

    public int getPunishDelay() {
        return this.punishDelay;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public boolean Ti() {
        return this.o;
    }

    private static we a(we we2) {
        return we2;
    }
}

