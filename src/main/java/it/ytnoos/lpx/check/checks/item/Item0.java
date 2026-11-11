/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.item;

import com.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBT;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTList;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.check.checks.item.DisableDecodingType;
import it.ytnoos.lpx.check.checks.item.NbtSizeLimit;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Item0
extends PlayerCheck<NBTDecodeEvent> {
    private static final int J = 32768;
    private static final int q = 262144;
    private DisableDecodingType disableItemDecoding;
    private NbtSizeLimit nbtSizeLimit;
    private int dialogPayloadMaxBytes;
    private RollingIntervalCounter rollingIntervalCounter;

    public Item0(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.disableItemDecoding = DisableDecodingType.CD(this.getOptions().getCheckOptions().getConfig().getString("options.disable-item-decoding", DisableDecodingType.DEFAULT.name()));
        this.nbtSizeLimit = NbtSizeLimit.bb();
        this.dialogPayloadMaxBytes = this.getOptions().getCheckOptions().getConfig().getInt("options.dialog-payload-max-bytes", 32768);
        this.rollingIntervalCounter = new RollingIntervalCounter(this.getOptions().getCheckOptions().getConfig().getInt("options.dialog-payload-bytes-per-second", 262144));
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, NBTDecodeEvent nBTDecodeEvent) {
        if (protocolPlayer.isProcessingPayload()) {
            return CheckResult.ALLOW;
        }
        Player player = protocolPlayer.getPlayer().orElse(null);
        int n = nBTDecodeEvent.getNbt().getDecodedSizeBytes();
        if (this.disableItemDecoding != DisableDecodingType.NONE && this._W(protocolPlayer)) {
            if (n < this.dialogPayloadMaxBytes) {
                this.rollingIntervalCounter.increment(n);
                if (!this.rollingIntervalCounter.isOverLimit()) {
                    return CheckResult.ALLOW;
                }
            }
            return this.replaceNbt(nBTDecodeEvent, true);
        }
        boolean bl = player != null && player.getGameMode() == GameMode.CREATIVE;
        boolean bl2 = n >= this.nbtSizeLimit.getLimit(protocolPlayer);
        switch (this.disableItemDecoding) {
            case NONE: {
                return CheckResult.ALLOW;
            }
            case SMART: {
                if (bl2 && !bl) break;
                return CheckResult.ALLOW;
            }
            case ALL: {
                if (!bl) break;
                return CheckResult.ALLOW;
            }
        }
        boolean bl3 = !bl || this.disableItemDecoding != DisableDecodingType.CREATIVE;
        return this.replaceNbt(nBTDecodeEvent, bl3);
    }

    private boolean _W(ProtocolPlayer protocolPlayer) {
        ReceivedPacket receivedPacket = protocolPlayer.getLastReceivedPacket();
        if (receivedPacket == null) {
            return false;
        }
        PacketTypeCommon packetTypeCommon = receivedPacket.getPacketType();
        return packetTypeCommon == PacketType.Play.Client.CUSTOM_CLICK_ACTION || packetTypeCommon == PacketType.Configuration.Client.CUSTOM_CLICK_ACTION;
    }

    private CheckResult replaceNbt(NBTDecodeEvent nBTDecodeEvent, boolean bl) {
        NBT nBT = nBTDecodeEvent.getNbt();
        ReceivedPacket receivedPacket = this.player.getLastReceivedPacket();
        Object var7_5 = null;
        if (receivedPacket != null && this.player.isLegacyClientOnHashedServer()) {
            receivedPacket.addOriginalNbt(nBT instanceof NBTList ? nBT.copy() : nBT);
        }
        if (nBT instanceof NBTCompound) {
            nBTDecodeEvent.setNbt(new NBTCompound());
        } else if (nBT instanceof NBTString) {
            if (bl || ((NBTString)nBT).getValue().length() >= 256) {
                nBTDecodeEvent.setNbt(new NBTString(""));
            }
        } else if (nBT instanceof NBTList) {
            NBTList nBTList = (NBTList)nBT;
            if (bl) {
                nBTDecodeEvent.setNbt(new NBTList<NBTList>(nBTList.getType(), 0));
            } else {
                for (int i = nBTList.size() - 1; i >= 0; --i) {
                    Object t = nBTList.getTag(i);
                    if (t instanceof NBTString) {
                        if (((NBTString)t).getValue().length() < 256) continue;
                        nBTList.removeTag(i);
                        continue;
                    }
                    nBTList.removeTag(i);
                    if (var7_5 == null) continue;
                }
                nBTDecodeEvent.setNbt(nBTList);
            }
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

