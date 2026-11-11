/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.payload;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PayloadE
extends PlayerCheck<WrapperPlayClientPluginMessage> {
    public PayloadE(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (!wrapperPlayClientPluginMessage.getChannelName().equals("REGISTER")) {
            return CheckResult.ALLOW;
        }
        String string = "";
        try {
            string = new String(wrapperPlayClientPluginMessage.getData());
            for (String string2 : string.split("\u0000")) {
                CraftPlayer.class.getMethod("addChannel", String.class).invoke(player, string2);
            }
        }
        catch (Exception exception) {
            this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[]{FlagInfo.of("channels", string)});
        }
        return CheckResult.DENY;
    }
}

