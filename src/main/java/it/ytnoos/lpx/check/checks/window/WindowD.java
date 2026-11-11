/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.window;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Player;

public class WindowD
extends PlayerCheck<WrapperPlayClientClickWindow> {
    public WindowD(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        InventoryViewAdapter inventoryViewAdapter = protocolPlayer.lpx().getInventoryViewAdapter(player);
        if (inventoryViewAdapter.isNull()) {
            return CheckResult.ALLOW;
        }
        String string = inventoryViewAdapter.getType();
        WrapperPlayClientClickWindow.WindowClickType windowClickType = wrapperPlayClientClickWindow.getWindowClickType();
        if (string.contains("LECTERN") && windowClickType == WrapperPlayClientClickWindow.WindowClickType.QUICK_MOVE) {
            this.Fn(wrapperPlayClientClickWindow, new FlagInfo[]{FlagInfo.of("inventory", string), FlagInfo.of("click-type", windowClickType.name())});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

