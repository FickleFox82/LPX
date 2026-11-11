/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 */
package it.ytnoos.lpx.inventories;

import com.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.inventories.items.ItemBuilder;
import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryProvider;
import it.ytnoos.lpx.we;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainInventory
implements InventoryProvider {
    private final LPXBukkit lpxBukkit;

    public MainInventory(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        String string = this.lpxBukkit.getConfigManager().getMessagesConfig().getMainColor();
        String string2 = this.lpxBukkit.getConfigManager().getMessagesConfig().getSecondColor();
        String string3 = PacketEvents.getAPI().getServerManager().getVersion().getReleaseName();
        String string4 = string2;
        inventoryContents.y(0, 0, ClickableItem.empty(ItemBuilder.DZ(Material.WATER_BUCKET).Ad(string + "Protocol version: " + string4 + string3).toItemStack()));
        if (player.hasPermission("lpx.reload")) {
            inventoryContents.y(0, 4, ClickableItem.bM(ItemBuilder.DZ(Material.NETHER_STAR).Ad(string + "Reload Config").toItemStack(), inventoryClickEvent -> {
                player.performCommand("lpx reload");
                player.closeInventory();
            }));
        } else {
            inventoryContents.y(0, 4, ClickableItem.empty(ItemBuilder.DZ(Material.BARRIER).Ad(string + "Reload Config")._I(new String[]{"<red><bold>NO PERMISSION"}).toItemStack()));
        }
        inventoryContents.y(0, 8, ClickableItem.bM(ItemBuilder.DZ(Material.BOOK).Ad(string + "Recent violations").toItemStack(), inventoryClickEvent -> this.lpxBukkit.getInventoryContainer().Ba().open(player)));
    }

    public SmartInventory zu() {
        return SmartInventory.PF().Wa("main").Dd(this).Jv(this.lpxBukkit.getInventoryManager()).wn(1, 9).W3(this.lpxBukkit.getConfigManager().getMessagesConfig().getMainColor() + "<bold>LPX >><reset>" + this.lpxBukkit.getConfigManager().getMessagesConfig().getSecondColor() + " Main GUI").YA();
    }

    private static we a(we we2) {
        return we2;
    }
}

