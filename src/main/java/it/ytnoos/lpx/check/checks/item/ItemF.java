/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class ItemF
extends PlayerCheck<NBTDecodeEvent> {
    private boolean replace;

    public ItemF(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.replace = this.getBoolean("replace", true);
    }

    public CheckResult Fl(Object object, NBTDecodeEvent nBTDecodeEvent) {
        if (!(nBTDecodeEvent.getNbt() instanceof NBTCompound)) {
            return CheckResult.ALLOW;
        }
        NBTList<NBTString> nBTList = ((NBTCompound)nBTDecodeEvent.getNbt()).getStringListTagOrNull("pages");
        if (nBTList == null) {
            return CheckResult.ALLOW;
        }
        if (!this.replace) {
            return CheckResult.ALLOW;
        }
        for (int i = 0; i < nBTList.size(); ++i) {
            NBTString nBTString = nBTList.getTag(i);
            nBTString.setValue(nBTString.getValue().replace('.', '\u2024'));
        }
        return CheckResult.CHANGE;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

