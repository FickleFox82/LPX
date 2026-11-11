/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class ItemB
extends PlayerCheck<NBTDecodeEvent> {
    private int max;
    private int maxModern;

    public ItemB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getInt("max", 50);
        this.maxModern = this.getInt("max-modern", 100);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        int n;
        NBT nBT = nBTDecodeEvent.getNbt();
        if (!(nBT instanceof NBTCompound)) {
            return CheckResult.ALLOW;
        }
        NBTList<NBTString> nBTList = ((NBTCompound)nBT).getStringListTagOrNull("pages");
        if (nBTList == null) {
            return CheckResult.ALLOW;
        }
        boolean bl = protocolPlayer.getUser().getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_14);
        int n2 = n = bl ? this.maxModern : this.max;
        if (nBTList.size() <= n) {
            return CheckResult.ALLOW;
        }
        this.Fn(nBTDecodeEvent, new FlagInfo[]{FlagInfo.of("pages", nBTList.size()), FlagInfo.of("max", n), FlagInfo.of("modern", bl)});
        return CheckResult.DENY;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

