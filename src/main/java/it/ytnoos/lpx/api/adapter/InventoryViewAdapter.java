/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.Inventory
 */
package it.ytnoos.lpx.api.adapter;

import org.bukkit.inventory.Inventory;

public interface InventoryViewAdapter {
    public boolean isNull();

    public String getType();

    public Inventory getTopInventory();

    public Inventory getBottomInventory();

    public int countSlots();

    public int convertSlot(int var1);
}

