/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    protected final LPXBukkit lpxBukkit;
    private final String w;
    private final String Q;
    private final String C;
    private final boolean playerOnly;
    private final String X;

    protected SubCommand(LPXBukkit lPXBukkit, String string, String string2, String string3, boolean bl) {
        this(lPXBukkit, string, string2, string3, bl, "");
    }

    protected SubCommand(LPXBukkit lPXBukkit, String string, String string2, String string3, boolean bl, String string4) {
        this.lpxBukkit = lPXBukkit;
        this.w = string;
        this.Q = string2;
        this.C = string3;
        this.playerOnly = bl;
        this.X = string4;
    }

    public abstract void execute(CommandSender var1, String[] var2);

    public List tabComplete(CommandSender commandSender, String[] stringArray) {
        return Collections.emptyList();
    }

    public String LB() {
        return this.w;
    }

    public String LU() {
        return this.Q;
    }

    public String LS() {
        return this.C;
    }

    public boolean isPlayerOnly() {
        return this.playerOnly;
    }

    public String L0() {
        return this.X;
    }

    public void sendMessage(CommandSender commandSender, String string) {
        MessageFormatter.send(commandSender, this.lpxBukkit.getConfigManager().getMessagesConfig().getPrefix() + " " + string);
    }
}

