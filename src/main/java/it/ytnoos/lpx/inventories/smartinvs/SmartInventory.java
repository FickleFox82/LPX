/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 */
package it.ytnoos.lpx.inventories.smartinvs;

import it.ytnoos.lpx.inventories.smartinvs.InventoryListener;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryProvider;
import it.ytnoos.lpx.inventories.smartinvs.opener.InventoryOpener;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class SmartInventory {
    private final InventoryManager inventoryManager;
    private String X;
    private String c;
    private InventoryType type;
    private int i;
    private int o;
    private boolean closeable;
    private InventoryProvider inventoryProvider;
    private SmartInventory smartInventory;
    private List<InventoryListener<? extends Event>> listeners;
    private static final String b = "No opener found for the inventory type ";

    private SmartInventory(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public static Builder PF() {
        return new Builder();
    }

    public Inventory open(Player player) {
        return this.open(player, 0);
    }

    public Inventory open(Player player, int n) {
        Optional optional = this.inventoryManager.gT(player);
        optional.ifPresent(smartInventory -> {
            smartInventory.getListeners().stream().filter(inventoryListener -> inventoryListener.getType() == InventoryCloseEvent.class).forEach(inventoryListener -> inventoryListener.accept(new InventoryCloseEvent(player.getOpenInventory())));
            this.inventoryManager.da(player, null);
        });
        InventoryContents.Impl impl = new InventoryContents.Impl(this, player);
        impl.getPagination().a(n);
        this.inventoryManager.XE(player, impl);
        this.inventoryProvider.init(player, impl);
        InventoryOpener inventoryOpener = (InventoryOpener)this.inventoryManager.findOpener(this.type).orElseThrow(() -> new IllegalStateException(b + this.type.name()));
        Inventory inventory = inventoryOpener.m(this, player);
        this.inventoryManager.da(player, this);
        return inventory;
    }

    public void close(Player player) {
        this.listeners.stream().filter(inventoryListener -> inventoryListener.getType() == InventoryCloseEvent.class).forEach(inventoryListener -> inventoryListener.accept(new InventoryCloseEvent(player.getOpenInventory())));
        this.inventoryManager.da(player, null);
        player.closeInventory();
        this.inventoryManager.XE(player, null);
    }

    public String Py() {
        return this.X;
    }

    public String Pq() {
        return this.c;
    }

    public InventoryType getType() {
        return this.type;
    }

    public int Pr() {
        return this.i;
    }

    public int PH() {
        return this.o;
    }

    public boolean isCloseable() {
        return this.closeable;
    }

    public void setCloseable(boolean bl) {
        this.closeable = bl;
    }

    public InventoryProvider getInventoryProvider() {
        return this.inventoryProvider;
    }

    public Optional getParent() {
        return Optional.ofNullable(this.smartInventory);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    List getListeners() {
        return this.listeners;
    }

    public static final class Builder {
        private final List<InventoryListener<? extends Event>> listeners = new ArrayList<InventoryListener<? extends Event>>();
        private String U = "unknown";
        private String p = "";
        private InventoryType type = InventoryType.CHEST;
        private int t = 6;
        private int l = 9;
        private boolean closeable = true;
        private InventoryManager inventoryManager;
        private InventoryProvider inventoryProvider;
        private SmartInventory smartInventory;

        private Builder() {
        }

        public Builder Wa(String string) {
            this.U = string;
            return this;
        }

        public Builder W3(String string) {
            this.p = MessageFormatter.k3(string);
            return this;
        }

        public Builder ys(InventoryType inventoryType) {
            this.type = inventoryType;
            return this;
        }

        public Builder wn(int n, int n2) {
            this.t = n;
            this.l = n2;
            return this;
        }

        public Builder JP(boolean bl) {
            this.closeable = bl;
            return this;
        }

        public Builder Dd(InventoryProvider inventoryProvider) {
            this.inventoryProvider = inventoryProvider;
            return this;
        }

        public Builder Qe(SmartInventory smartInventory) {
            this.smartInventory = smartInventory;
            return this;
        }

        public Builder fo(Object object) {
            this.listeners.add((InventoryListener<? extends Event>)object);
            return this;
        }

        public Builder Jv(InventoryManager inventoryManager) {
            this.inventoryManager = inventoryManager;
            return this;
        }

        public SmartInventory YA() {
            if (this.inventoryProvider == null) {
                throw new IllegalStateException("The provider of the SmartInventory.Builder must be set.");
            }
            InventoryManager inventoryManager = this.inventoryManager;
            if (inventoryManager == null) {
                throw new IllegalStateException("The manager of the SmartInventory.Builder must be set, or the SmartInvs should be loaded as a plugin.");
            }
            SmartInventory smartInventory = new SmartInventory(inventoryManager);
            smartInventory.X = this.U;
            smartInventory.c = this.p;
            smartInventory.type = this.type;
            smartInventory.i = this.t;
            smartInventory.o = this.l;
            smartInventory.closeable = this.closeable;
            smartInventory.inventoryProvider = this.inventoryProvider;
            smartInventory.smartInventory = this.smartInventory;
            smartInventory.listeners = this.listeners;
            return smartInventory;
        }
    }
}

