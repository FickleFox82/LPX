/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package it.ytnoos.lpx.inventories.smartinvs;

import java.util.function.Consumer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickableItem {
    private final ItemStack item;
    private final Consumer<InventoryClickEvent> consumer;
    private final String z = "forged";

    private ClickableItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.item = itemStack;
        this.consumer = consumer;
    }

    public static ClickableItem empty(ItemStack itemStack) {
        return ClickableItem.bM(itemStack, inventoryClickEvent -> {});
    }

    public static ClickableItem bM(ItemStack itemStack, Consumer consumer) {
        return new ClickableItem(itemStack, consumer);
    }

    public void O5(Object object) {
        this.consumer.accept((InventoryClickEvent)object);
    }

    public ItemStack getItem() {
        return this.item;
    }
}

