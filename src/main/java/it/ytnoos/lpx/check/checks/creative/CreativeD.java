/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.creative;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.utils.ItemUtils;
import it.ytnoos.lpx.we;

public class CreativeD
extends PlayerCheck<WrapperPlayClientCreativeInventoryAction> {
    private int max;

    public CreativeD(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getInt("max", 16384);
    }

    public CheckResult hi(Object object, WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        ItemStack itemStack = wrapperPlayClientCreativeInventoryAction.getItemStack();
        NBTList nBTList = ItemUtils.TT(itemStack).orElse(null);
        if (nBTList == null || nBTList.isEmpty()) {
            return CheckResult.ALLOW;
        }
        int n = 0;
        for (NBTString nBTString : nBTList.getTags()) {
            n += nBTString.getValue().length();
        }
        if (n <= this.max) {
            return CheckResult.ALLOW;
        }
        this.Fn(wrapperPlayClientCreativeInventoryAction, new FlagInfo[]{FlagInfo.of("lore-length", n)});
        return CheckResult.DENY;
    }

    private static we a(we we2) {
        return we2;
    }
}

