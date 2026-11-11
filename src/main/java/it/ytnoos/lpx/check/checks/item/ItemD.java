/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.checks.item.ItemA;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class ItemD
extends PlayerCheck<ItemStackDecodeEvent> {
    private int maxBytes;
    private int resolvedTolerance;
    private boolean resize;

    public ItemD(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.maxBytes = this.getInt("max-bytes", 255);
        this.resolvedTolerance = this.getInt("resolved-tolerance", 3);
        this.resize = this.getBoolean("resize", true);
    }

    public CheckResult mj(Object object, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBTCompound nBTCompound = itemStackDecodeEvent.getItemStack().getNBT();
        if (nBTCompound == null) {
            return CheckResult.ALLOW;
        }
        NBTList<NBTString> nBTList = nBTCompound.getStringListTagOrNull("pages");
        if (nBTList == null) {
            return CheckResult.ALLOW;
        }
        boolean bl = false;
        boolean bl2 = ItemA.isResolved(itemStackDecodeEvent.getItemStack());
        for (int i = 0; i < nBTList.size(); ++i) {
            NBTString nBTString = nBTList.getTag(i);
            byte[] byArray = nBTString.getValue().getBytes();
            int n = byArray.length;
            if (n <= this.maxBytes) continue;
            if (!bl2 || n > this.maxBytes * this.resolvedTolerance) {
                this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("pages", nBTList.size()), FlagInfo.of("page-index", i), FlagInfo.of("page-bytes", n), FlagInfo.of("resolved", bl2)});
                return CheckResult.DENY;
            }
            if (!this.resize) continue;
            nBTString.setValue(new String(byArray, 0, this.maxBytes));
            bl = true;
        }
        return bl ? CheckResult.CHANGE : CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

