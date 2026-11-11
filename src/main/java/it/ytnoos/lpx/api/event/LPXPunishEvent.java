/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.jetbrains.annotations.NotNull
 */
package it.ytnoos.lpx.api.event;

import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.we;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LPXPunishEvent
extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ProtocolPlayer protocolPlayer;
    private final Check<?> check;

    public LPXPunishEvent(ProtocolPlayer protocolPlayer, Check<?> check) {
        this.protocolPlayer = protocolPlayer;
        this.check = check;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public ProtocolPlayer getProtocolPlayer() {
        return this.protocolPlayer;
    }

    public Check<?> getCheck() {
        return this.check;
    }

    private static we a(we we2) {
        return we2;
    }
}

