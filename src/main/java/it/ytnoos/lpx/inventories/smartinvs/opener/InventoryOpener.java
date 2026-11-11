/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 */
package it.ytnoos.lpx.inventories.smartinvs.opener;

import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public interface InventoryOpener {
    public Inventory m(SmartInventory var1, Player var2);

    public boolean supports(InventoryType var1);

    default public void i(Inventory inventory, InventoryContents inventoryContents) {
        ClickableItem[][] clickableItemArray = inventoryContents.getClickableItems();
        for (int i = 0; i < clickableItemArray.length; ++i) {
            for (int j = 0; j < clickableItemArray[i].length; ++j) {
                if (clickableItemArray[i][j] == null) continue;
                inventory.setItem(9 * i + j, clickableItemArray[i][j].getItem());
            }
        }
    }

    private static we b(we we2) {
        return we2;
    }
}

