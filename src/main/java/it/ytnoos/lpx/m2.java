/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.y;

public class m2
extends y {
    public m2(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions, PacketType.Play.Client.SET_TEST_BLOCK);
    }
}

