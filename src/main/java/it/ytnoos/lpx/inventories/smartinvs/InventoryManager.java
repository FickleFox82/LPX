/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package it.ytnoos.lpx.inventories.smartinvs;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.opener.ChestInventoryOpener;
import it.ytnoos.lpx.inventories.smartinvs.opener.InventoryOpener;
import it.ytnoos.lpx.inventories.smartinvs.opener.SpecialInventoryOpener;
import it.ytnoos.lpx.we;
import it.ytnoos.lpx.wq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class InventoryManager {
    private final Plugin plugin;
    private final LPXBukkit lpxBukkit;
    private final PluginManager pluginManager;
    private final Map<Player, SmartInventory> X;
    private final Map<Player, InventoryContents> S;
    private final List<InventoryOpener> Y;
    private final List<InventoryOpener> b;

    public InventoryManager(LPXBukkit lPXBukkit) {
        this.plugin = lPXBukkit.getPlugin();
        this.lpxBukkit = lPXBukkit;
        this.pluginManager = Bukkit.getPluginManager();
        this.X = new HashMap<Player, SmartInventory>();
        this.S = new HashMap<Player, InventoryContents>();
        this.Y = Arrays.asList(new ChestInventoryOpener(), new SpecialInventoryOpener());
        this.b = new ArrayList<InventoryOpener>();
    }

    public void init() {
        this.pluginManager.registerEvents((Listener)new wq(this), this.plugin);
    }

    public Optional findOpener(InventoryType inventoryType) {
        Optional<InventoryOpener> optional = this.b.stream().filter(inventoryOpener -> inventoryOpener.supports(inventoryType)).findAny();
        if (!optional.isPresent()) {
            optional = this.Y.stream().filter(inventoryOpener -> inventoryOpener.supports(inventoryType)).findAny();
        }
        return optional;
    }

    public void F_(InventoryOpener[] inventoryOpenerArray) {
        this.b.addAll(Arrays.asList(inventoryOpenerArray));
    }

    public List getOpenedPlayers(SmartInventory smartInventory) {
        ArrayList arrayList = new ArrayList();
        this.X.forEach((player, smartInventory2) -> {
            if (smartInventory.equals(smartInventory2)) {
                arrayList.add(player);
            }
        });
        return arrayList;
    }

    public Optional gT(Object object) {
        return Optional.ofNullable(this.X.get(object));
    }

    protected void da(Player player, SmartInventory smartInventory) {
        if (smartInventory == null) {
            this.X.remove(player);
        } else {
            this.X.put(player, smartInventory);
        }
    }

    public Optional gm(Object object) {
        return Optional.ofNullable(this.S.get(object));
    }

    protected void XE(Player player, InventoryContents inventoryContents) {
        if (inventoryContents == null) {
            this.S.remove(player);
        } else {
            this.S.put(player, inventoryContents);
        }
    }

    private static we a(we we2) {
        return we2;
    }
}

