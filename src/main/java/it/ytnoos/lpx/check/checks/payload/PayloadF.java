/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.payload;

import com.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import org.bukkit.entity.Player;

public class PayloadF
extends PlayerCheck<WrapperPlayClientPluginMessage> {
    public PayloadF(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!wrapperPlayClientPluginMessage.getChannelName().equalsIgnoreCase("purpur:beehive_c2s")) {
            return CheckResult.ALLOW;
        }
        ByteBuf byteBuf = null;
        try {
            byteBuf = Unpooled.copiedBuffer((byte[])wrapperPlayClientPluginMessage.getData());
            Vector3i vector3i = new Vector3i(ByteBufHelper.readLong(byteBuf));
            if (!player.getWorld().isChunkLoaded(vector3i.getX() >> 4, vector3i.getZ() >> 4)) {
                this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[]{FlagInfo.of("blockpos", vector3i)});
                CheckResult checkResult = CheckResult.DENY;
                return checkResult;
            }
        }
        catch (Exception exception) {
            this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[]{FlagInfo.of("exception", exception.getMessage())});
            CheckResult checkResult = CheckResult.DENY;
            return checkResult;
        }
        finally {
            if (byteBuf != null && byteBuf.refCnt() > 0) {
                byteBuf.release();
            }
        }
        return CheckResult.ALLOW;
    }
}

