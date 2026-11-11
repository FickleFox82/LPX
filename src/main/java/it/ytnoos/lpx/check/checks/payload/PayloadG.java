/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 */
package it.ytnoos.lpx.check.checks.payload;

import com.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import io.netty.buffer.Unpooled;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.BukkitCheckManager;
import it.ytnoos.lpx.check.WrapperUtils;
import it.ytnoos.lpx.common.check.PlayerCheck;

public class PayloadG
extends PlayerCheck<WrapperPlayClientPluginMessage> {
    private final boolean preVia;
    private static final String EXCEPTION = "exception";

    public PayloadG(ProtocolPlayer protocolPlayer, CheckOptions checkOptions, boolean bl) {
        super(protocolPlayer, checkOptions);
        this.preVia = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        CheckResult checkResult;
        CheckResult checkResult2;
        Object object;
        ItemStack itemStack;
        PacketWrapper packetWrapper;
        PacketWrapper packetWrapper2;
        block10: {
            block9: {
                if (!BukkitCheckManager.BOOK_CHANNELS.contains(wrapperPlayClientPluginMessage.getChannelName())) {
                    return CheckResult.ALLOW;
                }
                if (!protocolPlayer.hasAnyCheck(ItemStackDecodeEvent.class, this.preVia)) {
                    return CheckResult.ALLOW;
                }
                packetWrapper2 = null;
                packetWrapper = null;
                packetWrapper2 = WrapperUtils.ST(protocolPlayer, Unpooled.copiedBuffer((byte[])wrapperPlayClientPluginMessage.getData()), this.preVia);
                itemStack = packetWrapper2.readItemStack();
                object = itemStack.getNBT();
                protocolPlayer.setProcessingPayload(true);
                checkResult2 = CheckResult.ALLOW;
                if (object == null || (checkResult2 = protocolPlayer.handleChecks(NBTDecodeEvent.class, new NBTDecodeEvent(wrapperPlayClientPluginMessage, null, (NBT)object), this.preVia).result()) != CheckResult.DENY) break block9;
                CheckResult checkResult3 = checkResult2;
                protocolPlayer.setProcessingPayload(false);
                WrapperUtils.releaseWrapper(packetWrapper2);
                WrapperUtils.releaseWrapper(packetWrapper);
                return checkResult3;
            }
            checkResult2 = checkResult2.merge(protocolPlayer.handleChecks(ItemStackDecodeEvent.class, new ItemStackDecodeEvent(wrapperPlayClientPluginMessage, null, itemStack), this.preVia).result());
            if (checkResult2 != CheckResult.DENY) break block10;
            CheckResult checkResult4 = checkResult2;
            protocolPlayer.setProcessingPayload(false);
            WrapperUtils.releaseWrapper(packetWrapper2);
            WrapperUtils.releaseWrapper(packetWrapper);
            return checkResult4;
        }
        try {
            if (checkResult2 == CheckResult.CHANGE) {
                packetWrapper = WrapperUtils.ST(protocolPlayer, Unpooled.buffer(), this.preVia);
                packetWrapper.writeItemStack(itemStack);
                ByteBufHelper.capacity(packetWrapper.buffer, ByteBufHelper.writerIndex(packetWrapper.buffer));
                wrapperPlayClientPluginMessage.setData(ByteBufHelper.array(packetWrapper.buffer));
            }
            checkResult = checkResult2;
            protocolPlayer.setProcessingPayload(false);
        }
        catch (Throwable throwable) {
            try {
                this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[]{FlagInfo.of(EXCEPTION, throwable.getMessage())});
                object = CheckResult.DENY;
                protocolPlayer.setProcessingPayload(false);
            }
            catch (Throwable throwable2) {
                protocolPlayer.setProcessingPayload(false);
                WrapperUtils.releaseWrapper(packetWrapper2);
                WrapperUtils.releaseWrapper(packetWrapper);
                throw throwable2;
            }
            WrapperUtils.releaseWrapper(packetWrapper2);
            WrapperUtils.releaseWrapper(packetWrapper);
            return object;
        }
        WrapperUtils.releaseWrapper(packetWrapper2);
        WrapperUtils.releaseWrapper(packetWrapper);
        return checkResult;
    }

    @Override
    public boolean isPreVia() {
        return this.preVia;
    }
}

