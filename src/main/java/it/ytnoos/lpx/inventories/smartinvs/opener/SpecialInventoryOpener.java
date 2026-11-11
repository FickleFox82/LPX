/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 */
package it.ytnoos.lpx.inventories.smartinvs.opener;

import com.google.common.collect.ImmutableList;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.opener.InventoryOpener;
import it.ytnoos.lpx.we;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SpecialInventoryOpener
implements InventoryOpener {
    private static final List<InventoryType> SUPPORTED = ImmutableList.of((Object)InventoryType.FURNACE, (Object)InventoryType.WORKBENCH, (Object)InventoryType.DISPENSER, (Object)InventoryType.DROPPER, (Object)InventoryType.ENCHANTING, (Object)InventoryType.BREWING, (Object)InventoryType.ANVIL, (Object)InventoryType.BEACON, (Object)InventoryType.HOPPER);

    @Override
    public Inventory m(SmartInventory smartInventory, Player player) {
        InventoryManager inventoryManager = smartInventory.getInventoryManager();
        Inventory inventory = Bukkit.createInventory((InventoryHolder)player, (InventoryType)smartInventory.getType(), (String)smartInventory.Pq());
        this.i(inventory, (InventoryContents)inventoryManager.gm(player).get());
        player.openInventory(inventory);
        return inventory;
    }

    @Override
    public boolean supports(InventoryType inventoryType) {
        return SUPPORTED.contains(inventoryType);
    }

    private static we a(we we2) {
        return we2;
    }
}

