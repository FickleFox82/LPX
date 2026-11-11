/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 */
package it.ytnoos.lpx.inventories.smartinvs.opener;

import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.opener.InventoryOpener;
import it.ytnoos.lpx.we;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ChestInventoryOpener
implements InventoryOpener {
    @Override
    public Inventory m(SmartInventory smartInventory, Player player) {
        InventoryManager inventoryManager = smartInventory.getInventoryManager();
        Inventory inventory = Bukkit.createInventory((InventoryHolder)player, (int)(smartInventory.Pr() * smartInventory.PH()), (String)smartInventory.Pq());
        this.i(inventory, (InventoryContents)inventoryManager.gm(player).get());
        player.openInventory(inventory);
        return inventory;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean supports(InventoryType inventoryType) {
        long l = 42248571798425L;
        InventoryType inventoryType2 = inventoryType;
        if (l > 1L) {
            if (inventoryType2 == InventoryType.CHEST) return true;
            inventoryType2 = inventoryType;
        }
        if (inventoryType2 != InventoryType.ENDER_CHEST) return false;
        return true;
    }

    private static we a(we we2) {
        return we2;
    }
}

