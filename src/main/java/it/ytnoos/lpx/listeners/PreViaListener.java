/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.listeners;

import com.github.retrooper.packetevents.event.PacketEvent;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.listeners.LPXListener;

public class PreViaListener
extends PacketListenerAbstract {
    private final LPXListener lpxListener;

    public PreViaListener(LPXListener lPXListener) {
        this.lpxListener = lPXListener;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        this.lpxListener.handleReceive(packetReceiveEvent, true);
    }

    @Override
    public void onPacketEventExternal(PacketEvent packetEvent) {
        this.lpxListener.handleDecode(packetEvent, true);
    }

    @Override
    public boolean isPreVia() {
        return true;
    }
}

