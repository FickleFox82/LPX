/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.inventories;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.inventories.MainInventory;
import it.ytnoos.lpx.inventories.ViolationsInventory;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;

public class InventoryContainer {
    private final LPXBukkit lpxBukkit;

    public InventoryContainer(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    public SmartInventory Ba() {
        return new ViolationsInventory(this.lpxBukkit).tG();
    }

    public SmartInventory Bu() {
        return new MainInventory(this.lpxBukkit).zu();
    }
}

