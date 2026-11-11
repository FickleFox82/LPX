/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 */
package it.ytnoos.lpx;

import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.we;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class wv
implements InventoryViewAdapter {
    private final InventoryView inventoryView;

    public wv(Player player) {
        this.inventoryView = player.getOpenInventory();
    }

    @Override
    public boolean isNull() {
        return this.inventoryView == null;
    }

    @Override
    public String getType() {
        return this.inventoryView.getType().name();
    }

    @Override
    public Inventory getTopInventory() {
        return this.inventoryView.getTopInventory();
    }

    @Override
    public Inventory getBottomInventory() {
        return this.inventoryView.getBottomInventory();
    }

    @Override
    public int countSlots() {
        return this.inventoryView.countSlots();
    }

    @Override
    public int convertSlot(int n) {
        return this.inventoryView.convertSlot(n);
    }

    private static we a(we we2) {
        return we2;
    }
}

