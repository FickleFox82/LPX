/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Container
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.InventoryView
 */
package it.ytnoos.lpx.check.checks.window;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import net.minecraft.server.v1_8_R3.Container;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class WindowA
extends PlayerCheck<WrapperPlayClientClickWindow> {
    public WindowA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        int n;
        Player player = protocolPlayer.getPlayer().orElse(null);
        if (player == null) {
            return CheckResult.ALLOW;
        }
        Container container = ((CraftPlayer)player).getHandle().activeContainer;
        if (container == null) {
            return CheckResult.ALLOW;
        }
        int n2 = wrapperPlayClientClickWindow.getSlot();
        if (n2 >= (n = container.c.size()) || n2 < 0 && n2 != -999 && n2 != -1) {
            InventoryView inventoryView = container.getBukkitView();
            this.F6(wrapperPlayClientClickWindow, new FlagInfo[]{FlagInfo.of("slot", n2), FlagInfo.of("size", n), FlagInfo.of("inventory", inventoryView.getType().name()), FlagInfo.of("top-inventory", inventoryView.getTopInventory().getType().name()), FlagInfo.of("bottom-inventory", inventoryView.getBottomInventory().getType().name())});
            return CheckResult.DENY;
        }
        this.zl();
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

