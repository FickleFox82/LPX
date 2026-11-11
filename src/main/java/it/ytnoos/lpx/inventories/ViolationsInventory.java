/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.ClickType
 */
package it.ytnoos.lpx.inventories;

import com.google.common.collect.Lists;
import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.check.PendingPunishment;
import it.ytnoos.lpx.inventories.items.ItemBuilder;
import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryProvider;
import it.ytnoos.lpx.inventories.smartinvs.content.Pagination;
import it.ytnoos.lpx.utils.string.MessageFormatter;
import it.ytnoos.lpx.we;
import it.ytnoos.lpx.wz;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ViolationsInventory
implements InventoryProvider {
    private final Format format = new SimpleDateFormat("dd/MM HH:mm:ss");
    private final LPXBukkit lpxBukkit;

    public ViolationsInventory(LPXBukkit lPXBukkit) {
        this.lpxBukkit = lPXBukkit;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        block3: {
            Pagination pagination = inventoryContents.getPagination();
            String string = this.lpxBukkit.getConfigManager().getMessagesConfig().getMainColor();
            String string2 = this.lpxBukkit.getConfigManager().getMessagesConfig().getSecondColor();
            ArrayList arrayList = Lists.newArrayList();
            for (PendingPunishment pendingPunishment : this.lpxBukkit.getRecentViolations()) {
                String string3 = pendingPunishment.getOptions().getCheckOptions().getPath();
                String string4 = string3.substring(0, string3.length() - 1);
                String string5 = string2;
                String string6 = string + "Config: " + string5 + string4;
                ArrayList<Object> arrayList2 = new ArrayList<Object>();
                arrayList2.add(string6);
                String string7 = this.convertTime(pendingPunishment.getTimestamp());
                String string8 = string2;
                arrayList2.add(string + "Time: " + string8 + string7);
                arrayList2.add("");
                arrayList2.add(string + "Infos:");
                for (FlagInfo flagInfo : pendingPunishment.getInfo()) {
                    String string9 = MessageFormatter.kp(String.valueOf(flagInfo.getValue()));
                    String string10 = string2;
                    String string11 = flagInfo.getName();
                    String string12 = string + string11 + ": " + string10 + string9;
                    arrayList2.add(string12);
                }
                arrayList2.add("");
                arrayList2.add(string2 + "Press Q to delete this log");
                arrayList.add(ClickableItem.bM(ItemBuilder.DZ(Material.BOOK).Ad(string + pendingPunishment.getName()).Is(arrayList2).toItemStack(), inventoryClickEvent -> {
                    if (inventoryClickEvent.getClick() != ClickType.DROP && inventoryClickEvent.getClick() != ClickType.CONTROL_DROP) {
                        return;
                    }
                    this.lpxBukkit.getRecentViolations().remove(pendingPunishment);
                    this.tG().open(player, this.getMaxPage(pagination.getPage()));
                }));
            }
            pagination.v(arrayList.toArray(new ClickableItem[0]));
            pagination.N(36);
            pagination.B(inventoryContents.Q(wz.HORIZONTAL, 0, 0));
            if (!pagination.R()) {
                inventoryContents.y(4, 0, ClickableItem.bM(new ItemBuilder(Material.ARROW).Ad(string2 + "Previous page").toItemStack(), inventoryClickEvent -> this.tG().open(player, pagination.t().getPage())));
            }
            if (pagination.p()) break block3;
            inventoryContents.y(4, 8, ClickableItem.bM(new ItemBuilder(Material.ARROW).Ad(string2 + "Next page").toItemStack(), inventoryClickEvent -> this.tG().open(player, pagination.q().getPage())));
        }
    }

    public SmartInventory tG() {
        return SmartInventory.PF().Wa("violations").Dd(this).Jv(this.lpxBukkit.getInventoryManager()).wn(5, 9).W3(this.lpxBukkit.getConfigManager().getMessagesConfig().getMainColor() + "<bold>LPX >><reset>" + this.lpxBukkit.getConfigManager().getMessagesConfig().getSecondColor() + " Recent Violations").YA();
    }

    private String convertTime(long l) {
        return this.format.format(new Date(l));
    }

    private int getMaxPage(int n) {
        int n2 = this.lpxBukkit.getRecentViolations().size();
        if (n2 == 0) {
            return 0;
        }
        int n3 = (n2 - 1) / 36;
        return Math.min(n, n3);
    }

    private static we a(we we2) {
        return we2;
    }
}

