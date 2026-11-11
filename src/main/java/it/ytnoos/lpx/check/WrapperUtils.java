/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package it.ytnoos.lpx.check;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.we;
import org.jetbrains.annotations.Nullable;

public class WrapperUtils {
    private WrapperUtils() {
    }

    public static PacketWrapper ST(ProtocolPlayer protocolPlayer, Object object, boolean bl) {
        PacketWrapper packetWrapper = new PacketWrapper(protocolPlayer.getUser().getClientVersion(), bl ? protocolPlayer.getUser().getClientVersion().toServerVersion() : PacketEvents.getAPI().getServerManager().getVersion(), -2);
        packetWrapper.buffer = object;
        packetWrapper.markReceivedData();
        return packetWrapper;
    }

    public static void releaseWrapper(@Nullable PacketWrapper packetWrapper) {
        if (packetWrapper == null) {
            return;
        }
        if (packetWrapper.buffer != null && ByteBufHelper.refCnt(packetWrapper.buffer) > 0) {
            ByteBufHelper.release(packetWrapper.buffer);
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

