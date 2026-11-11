/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.creative;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.utils.ItemUtils;
import it.ytnoos.lpx.we;

public class CreativeC
extends PlayerCheck<WrapperPlayClientCreativeInventoryAction> {
    private int max;

    public CreativeC(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getInt("max", 3000);
    }

    public CheckResult gZ(Object object, WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        ItemStack itemStack = wrapperPlayClientCreativeInventoryAction.getItemStack();
        String string = ItemUtils.T8(itemStack).orElse(null);
        if (string == null) {
            return CheckResult.ALLOW;
        }
        int n = string.length();
        if (n <= this.max) {
            return CheckResult.ALLOW;
        }
        this.Fn(wrapperPlayClientCreativeInventoryAction, new FlagInfo[]{FlagInfo.of("length", n), FlagInfo.of("display-name", string)});
        return CheckResult.DENY;
    }

    private static we a(we we2) {
        return we2;
    }
}

