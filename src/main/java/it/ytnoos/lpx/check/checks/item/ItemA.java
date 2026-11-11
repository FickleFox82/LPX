/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemType;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class ItemA
extends PlayerCheck<ItemStackDecodeEvent> {
    private static final String RESOLVED = "resolved";

    public ItemA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public static boolean isResolved(ItemStack itemStack) {
        ItemType itemType = itemStack.getType();
        boolean bl = itemType == ItemTypes.WRITTEN_BOOK;
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound != null && nBTCompound.getTags().containsKey("resolved")) {
            bl = nBTCompound.getBoolean("resolved");
        }
        return bl;
    }

    public CheckResult xs(Object object, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        ItemType itemType = itemStack.getType();
        if (ItemA.isResolved(itemStack) && itemType != ItemTypes.WRITTEN_BOOK) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("resolved", true), FlagInfo.of("item", itemType.getName())});
            return CheckResult.DENY;
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

