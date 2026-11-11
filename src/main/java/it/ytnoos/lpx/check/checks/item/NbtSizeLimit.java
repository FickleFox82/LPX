/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.we;

final class NbtSizeLimit {
    private static final int F;
    private static final int u;
    private final int p;
    private final int H;

    private NbtSizeLimit(int n, int n2) {
        this.p = n;
        this.H = n2;
    }

    static NbtSizeLimit bb() {
        return new NbtSizeLimit(4096, 2048);
    }

    int getLimit(ProtocolPlayer protocolPlayer) {
        return protocolPlayer.isLegacyClientOnHashedServer() ? this.H : this.p;
    }

    static {
        u = 2048;
        F = 4096;
    }

    private static we a(we we2) {
        return we2;
    }
}

