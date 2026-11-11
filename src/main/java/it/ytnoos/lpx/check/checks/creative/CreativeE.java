/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.creative;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemType;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.common.utils.NettyUtils;
import it.ytnoos.lpx.we;
import java.util.List;

public class CreativeE
extends PlayerCheck<ItemStackDecodeEvent> {
    private static final String[] BLACKLISTED = new String[]{"run_command", "translation.test.invalid"};
    private List<String> blacklist;

    public CreativeE(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.blacklist = this.getStringList("blacklist", BLACKLISTED);
    }

    public CheckResult Ck(Object object, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        ItemType itemType = itemStack.getType();
        if (itemType != ItemTypes.BOOK && itemType != ItemTypes.WRITABLE_BOOK && itemType != ItemTypes.WRITTEN_BOOK) {
            return CheckResult.ALLOW;
        }
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound == null) {
            return CheckResult.ALLOW;
        }
        for (String string : this.blacklist) {
            if (!NettyUtils.containsString((NBT)nBTCompound, string)) continue;
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("blacklisted", string)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

