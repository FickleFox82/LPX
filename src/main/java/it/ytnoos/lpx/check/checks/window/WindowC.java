/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.window;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Player;

public class WindowC
extends PlayerCheck<WrapperPlayClientClickWindow> {
    private static final String SLOT = "slot";

    public WindowC(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        if (protocolPlayer.lpx().getInventoryViewAdapter(player).isNull()) {
            this.Fn(wrapperPlayClientClickWindow, new FlagInfo[]{FlagInfo.of(SLOT, wrapperPlayClientClickWindow.getSlot())});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

