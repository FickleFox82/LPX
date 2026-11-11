/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package it.ytnoos.lpx.monitor;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.messages.MessagesConfig;
import it.ytnoos.lpx.monitor.PacketTickMonitor;
import it.ytnoos.lpx.we;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import org.bukkit.ChatColor;

public class HealthReport {
    private final DecimalFormat df = new DecimalFormat("#0");
    private final String Q;
    private final String n;

    public HealthReport(LPXBukkit lPXBukkit) {
        MessagesConfig messagesConfig = lPXBukkit.getConfigManager().getMessagesConfig();
        this.Q = messagesConfig.getMainColor();
        this.n = messagesConfig.getSecondColor();
    }

    public String eX(PacketTickMonitor packetTickMonitor) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.Q).append("Packet Tick Delay Report ").append(this.n).append("min").append(ChatColor.GRAY).append("/").append(this.n).append("med").append(ChatColor.GRAY).append("/").append(this.n).append("95%ile").append(ChatColor.GRAY).append("/").append(this.n).append("max\n");
        this.appendLine(stringBuilder, "10 seconds", packetTickMonitor.pa(), packetTickMonitor.pA(), packetTickMonitor.pK(), packetTickMonitor.pD());
        this.appendLine(stringBuilder, "minute", packetTickMonitor.p5(), packetTickMonitor.pQ(), packetTickMonitor.p2(), packetTickMonitor.pZ());
        this.appendLine(stringBuilder, "5 minutes", packetTickMonitor.ps(), packetTickMonitor.p3(), packetTickMonitor.pJ(), packetTickMonitor.ph());
        stringBuilder.append("\n \n");
        stringBuilder.append(this.Q).append("General status: ");
        double d = packetTickMonitor.px().doubleValue();
        stringBuilder.append(this.XL(d));
        stringBuilder.append(ChatColor.GRAY).append(" (90%ile: ").append(this.XT(d)).append(")\n");
        Map map = packetTickMonitor.getThreadPlayers();
        if (!map.isEmpty()) {
            stringBuilder.append("\n");
            stringBuilder.append(this.Q).append("Netty Threads:\n");
            ArrayList arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (Integer n : arrayList) {
                PacketTickMonitor.Sample sample = (PacketTickMonitor.Sample)map.get(n);
                double d2 = sample.getValue().doubleValue();
                int n2 = sample.getPlayerCount();
                stringBuilder.append(ChatColor.GRAY).append("[").append(n + 1).append("] ").append(this.XL(d2)).append(" ").append(ChatColor.GRAY);
                stringBuilder.append("(").append(n2).append(" ").append(n2 == 1 ? "player" : "players").append(")");
                if (this.isCritical(d2)) {
                    stringBuilder.append(" (90%ile: ").append(this.XT(d2)).append(") ");
                }
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private void appendLine(StringBuilder stringBuilder, String string, BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3, BigDecimal bigDecimal4) {
        double d;
        stringBuilder.append(this.n).append("last ").append(string).append(" ");
        if (bigDecimal != null) {
            d = bigDecimal.doubleValue();
            stringBuilder.append(this.getColor(d)).append(this.XT(d)).append(ChatColor.GRAY).append("/");
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?/");
        }
        if (bigDecimal2 != null) {
            d = bigDecimal2.doubleValue();
            stringBuilder.append(this.getColor(d)).append(this.XT(d)).append(ChatColor.GRAY).append("/");
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?/");
        }
        if (bigDecimal3 != null) {
            d = bigDecimal3.doubleValue();
            stringBuilder.append(this.getColor(d)).append(this.XT(d)).append(ChatColor.GRAY).append("/");
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?/");
        }
        if (bigDecimal4 != null) {
            d = bigDecimal4.doubleValue();
            stringBuilder.append(this.getColor(d)).append(this.XT(d));
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?");
        }
        stringBuilder.append("\n");
    }

    private String XT(double d) {
        return this.df.format(d);
    }

    private String XL(double d) {
        if (d == 0.0) {
            return String.valueOf(ChatColor.GREEN) + "HEALTHY :D";
        }
        if (d <= 1.0) {
            return String.valueOf(ChatColor.YELLOW) + "UNSTABLE :/";
        }
        if (d <= 2.0) {
            return String.valueOf(ChatColor.RED) + "BAD :(";
        }
        return String.valueOf(ChatColor.DARK_RED) + "VERY BAD D:";
    }

    private boolean isCritical(double d) {
        return d > 2.0;
    }

    private ChatColor getColor(double d) {
        if (d <= 1.0) {
            return ChatColor.GREEN;
        }
        if (d <= 2.0) {
            return ChatColor.YELLOW;
        }
        if (d <= 3.0) {
            return ChatColor.GOLD;
        }
        if (d <= 5.0) {
            return ChatColor.RED;
        }
        return ChatColor.DARK_RED;
    }

    private static we a(we we2) {
        return we2;
    }
}

