/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.FireworkExplosion;
import com.github.retrooper.packetevents.protocol.component.builtin.item.ItemFireworks;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.util.List;

public class ItemJ
extends PlayerCheck<ItemStackDecodeEvent> {
    private int F;
    private int b;

    public ItemJ(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.F = this.getConfig().getInt(this.getPath("max-list"), 12);
        this.b = this.getConfig().getInt(this.getPath("max-array"), 12);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        return protocolPlayer.usesComponents() ? this.SW(itemStack, itemStackDecodeEvent) : this.SL(itemStack, itemStackDecodeEvent);
    }

    private CheckResult SW(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        FireworkExplosion fireworkExplosion = itemStack.getComponentOr(ComponentTypes.FIREWORK_EXPLOSION, null);
        if (fireworkExplosion != null) {
            return this.checkExplosion(fireworkExplosion, itemStackDecodeEvent);
        }
        ItemFireworks itemFireworks = itemStack.getComponentOr(ComponentTypes.FIREWORKS, null);
        if (itemFireworks == null) {
            return CheckResult.ALLOW;
        }
        List<FireworkExplosion> list = itemFireworks.getExplosions();
        if (list.size() > this.F) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("explosions", list.size())});
            return CheckResult.DENY;
        }
        for (FireworkExplosion fireworkExplosion2 : list) {
            CheckResult checkResult = this.checkExplosion(fireworkExplosion2, itemStackDecodeEvent);
            if (checkResult == CheckResult.ALLOW) continue;
            return checkResult;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult checkExplosion(FireworkExplosion fireworkExplosion, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (fireworkExplosion.getColors().size() > this.b) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("colors", fireworkExplosion.getColors().size())});
            return CheckResult.DENY;
        }
        if (fireworkExplosion.getFadeColors().size() > this.b) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("fade-colors", fireworkExplosion.getFadeColors().size())});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult SL(ItemStack itemStack, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBTCompound nBTCompound = itemStack.getNBT();
        if (nBTCompound == null) {
            return CheckResult.ALLOW;
        }
        NBTCompound nBTCompound2 = nBTCompound.getCompoundTagOrNull("Explosion");
        if (nBTCompound2 != null) {
            return this.checkLegacyExplosion(nBTCompound2, itemStackDecodeEvent);
        }
        NBTCompound nBTCompound3 = nBTCompound.getCompoundTagOrNull("Fireworks");
        if (nBTCompound3 == null) {
            return CheckResult.ALLOW;
        }
        NBTList<NBTCompound> nBTList = nBTCompound3.getCompoundListTagOrNull("Explosions");
        if (nBTList == null) {
            return CheckResult.ALLOW;
        }
        if (nBTList.size() > this.F) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of("explosions", nBTList.size())});
            return CheckResult.DENY;
        }
        for (NBTCompound nBTCompound4 : nBTList.getTags()) {
            CheckResult checkResult = this.checkLegacyExplosion(nBTCompound4, itemStackDecodeEvent);
            if (checkResult == CheckResult.ALLOW) continue;
            return checkResult;
        }
        return CheckResult.ALLOW;
    }

    private CheckResult checkLegacyExplosion(NBTCompound nBTCompound, ItemStackDecodeEvent itemStackDecodeEvent) {
        CheckResult checkResult = this.checkColors(nBTCompound, "Colors", itemStackDecodeEvent);
        if (checkResult != CheckResult.ALLOW) {
            return checkResult;
        }
        return this.checkColors(nBTCompound, "FadeColors", itemStackDecodeEvent);
    }

    private CheckResult checkColors(NBTCompound nBTCompound, String string, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBTIntArray nBTIntArray;
        int n;
        NBT nBT = nBTCompound.getTagOrNull(string);
        if (nBT instanceof NBTIntArray && (n = (nBTIntArray = (NBTIntArray)nBT).getValue().length) > this.b) {
            this.Fn(itemStackDecodeEvent, new FlagInfo[]{FlagInfo.of(string.toLowerCase(), n)});
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

