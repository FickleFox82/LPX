/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.flood;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class FloodD
extends PlayerCheck<ItemStackDecodeEvent> {
    private RollingIntervalCounter k;
    private int bigThreshold;
    private RollingIntervalCounter m;

    public FloodD(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.k = new RollingIntervalCounter(this.getConfig().getInt(this.getPath("max"), 13));
        this.bigThreshold = this.getConfig().getInt(this.getPath("big-threshold"), 4000);
        this.m = new RollingIntervalCounter(this.getConfig().getInt(this.getPath("max-big"), 6));
    }

    protected CheckResult bX(Object object, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        if (itemStack.getType() != ItemTypes.WRITABLE_BOOK) {
            return CheckResult.ALLOW;
        }
        if (this.k.increment()) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("books-ps", this.k.getMaxCounter())});
            return CheckResult.DENY;
        }
        if (this.k.isOverLimit()) {
            return CheckResult.DENY;
        }
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound == null || !nBTCompound.getTags().containsKey("pages")) {
            return CheckResult.ALLOW;
        }
        int n = itemStack.getDecodedSizeBytes();
        if (n <= 0) {
            return CheckResult.ALLOW;
        }
        if (n > this.bigThreshold) {
            if (this.m.increment()) {
                this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("big-books-ps", this.m.getMaxCounter()), FlagInfo.of("size", n)});
                return CheckResult.DENY;
            }
            if (this.m.isOverLimit()) {
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }

    @Override
    public boolean isPreVia() {
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

