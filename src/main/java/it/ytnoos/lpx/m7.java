/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.y;

public class m7
extends y {
    public m7(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions, PacketType.Play.Client.TEST_INSTANCE_BLOCK_ACTION);
    }
}

