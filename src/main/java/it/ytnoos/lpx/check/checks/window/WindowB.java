/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.check.checks.window;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Player;

public class WindowB
extends PlayerCheck<WrapperPlayClientClickWindow> {
    private boolean cancelOnNewVersions;
    private ServerVersion serverVersion;
    private int newVersionMaxVl;

    public WindowB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.cancelOnNewVersions = this.getBoolean("cancel-on-new-versions", false);
        this.serverVersion = ServerVersion.V_1_21_5;
        this.newVersionMaxVl = Math.max(1, this.getInt("new-version-max-vl", 8));
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
        int n = wrapperPlayClientClickWindow.getSlot();
        int n2 = wrapperPlayClientClickWindow.getButton();
        WrapperPlayClientClickWindow.WindowClickType windowClickType = wrapperPlayClientClickWindow.getWindowClickType();
        int n3 = inventoryViewAdapter.countSlots();
        if (n >= n3 || windowClickType == WrapperPlayClientClickWindow.WindowClickType.SWAP && (n < 0 || n2 < 0)) {
            boolean bl = this.F6(wrapperPlayClientClickWindow, new FlagInfo[]{FlagInfo.of("action-number", wrapperPlayClientClickWindow.getActionNumber().orElse(null)), FlagInfo.of("action", windowClickType.name()), FlagInfo.of("button", wrapperPlayClientClickWindow.getButton()), FlagInfo.of("slot", n), FlagInfo.of("converted-slot", inventoryViewAdapter.convertSlot(n)), FlagInfo.of("size", n3), FlagInfo.of("inventory", inventoryViewAdapter.getType()), FlagInfo.of("top-inventory", inventoryViewAdapter.getTopInventory().getType().name()), FlagInfo.of("bottom-inventory", inventoryViewAdapter.getBottomInventory().getType().name())});
            if (this.shouldCancel(protocolPlayer) && !bl) {
                return CheckResult.ALLOW;
            }
            return CheckResult.DENY;
        }
        this.zl();
        return CheckResult.ALLOW;
    }

    private boolean shouldCancel(ProtocolPlayer protocolPlayer) {
        if (!this.cancelOnNewVersions) {
            return false;
        }
        ServerVersion serverVersion = protocolPlayer.getUser().getClientVersion().toServerVersion();
        if (serverVersion == null) {
            return false;
        }
        return serverVersion.isNewerThanOrEquals(this.serverVersion) && this.getOptions().getVl() < this.newVersionMaxVl;
    }

    private static we a(we we2) {
        return we2;
    }
}

