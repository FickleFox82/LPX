/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package it.ytnoos.lpx.inventories.smartinvs.content;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.Pagination;
import it.ytnoos.lpx.inventories.smartinvs.content.SlotIterator;
import it.ytnoos.lpx.inventories.smartinvs.content.SlotPos;
import it.ytnoos.lpx.we;
import it.ytnoos.lpx.wz;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InventoryContents {
    public SmartInventory getSmartInventory();

    public Pagination getPagination();

    public Optional T(Object var1);

    public SlotIterator r(Object var1, wz var2, int var3, int var4);

    public SlotIterator Q(wz var1, int var2, int var3);

    public SlotIterator o(String var1, wz var2, SlotPos var3);

    public SlotIterator j(wz var1, SlotPos var2);

    public ClickableItem[][] getClickableItems();

    public Optional O(long var1);

    public Optional A(int var1, long var2, int var4);

    public Optional E(int var1, int var2, SlotPos var3, int var4);

    public InventoryContents y(int var1, int var2, ClickableItem var3);

    public InventoryContents C(SlotPos var1, ClickableItem var2);

    public InventoryContents f(ClickableItem var1, long var2);

    public InventoryContents h(ClickableItem var1, long var2);

    public InventoryContents c(int var1, long var2, ClickableItem var4);

    public InventoryContents b(int var1, ClickableItem var2, long var3);

    public InventoryContents Z(long var1, ClickableItem var3);

    public InventoryContents D(int var1, long var2, int var4, int var5, int var6, ClickableItem var7);

    public InventoryContents e(SlotPos var1, SlotPos var2, long var3, ClickableItem var5);

    public Object V(Object var1);

    public Object w(long var1, String var3, Object var4);

    public InventoryContents J(Object var1, Object var2);

    public static class Impl
    implements InventoryContents {
        private final SmartInventory smartInventory;
        private final Player player;
        private final ClickableItem[][] clickableItems;
        private final Pagination pagination = new Pagination.Impl();
        private final Map<String, SlotIterator> A = new HashMap<String, SlotIterator>();
        private final Map<String, Object> l = new HashMap<String, Object>();

        public Impl(SmartInventory smartInventory, Player player) {
            this.smartInventory = smartInventory;
            this.player = player;
            this.clickableItems = new ClickableItem[smartInventory.Pr()][smartInventory.PH()];
        }

        @Override
        public SmartInventory getSmartInventory() {
            return this.smartInventory;
        }

        @Override
        public Pagination getPagination() {
            return this.pagination;
        }

        @Override
        public Optional T(Object object) {
            return Optional.ofNullable(this.A.get(object));
        }

        @Override
        public SlotIterator r(Object object, wz wz2, int n, int n2) {
            SlotIterator.Impl impl = new SlotIterator.Impl(this, this.smartInventory, wz2, n, n2);
            this.A.put((String)object, impl);
            return impl;
        }

        @Override
        public SlotIterator o(String string, wz wz2, SlotPos slotPos) {
            return this.r(string, wz2, slotPos.Np(), slotPos.N3());
        }

        @Override
        public SlotIterator Q(wz wz2, int n, int n2) {
            return new SlotIterator.Impl(this, this.smartInventory, wz2, n, n2);
        }

        @Override
        public SlotIterator j(wz wz2, SlotPos slotPos) {
            return this.Q(wz2, slotPos.Np(), slotPos.N3());
        }

        @Override
        public ClickableItem[][] getClickableItems() {
            return this.clickableItems;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Optional O(long l) {
            long l2 = l ^ 0x7BB9C9AD7140L;
            int n = 0;
            PacketWrapper[] packetWrapperArray = wz.getPacketWrappers();
            block0: while (n < this.clickableItems.length) {
                int n2 = 0;
                while (true) {
                    if (n2 >= this.clickableItems[0].length) {
                        ++n;
                        if (0L <= l) continue block0;
                    }
                    Optional optional = this.A(n, l2, n2);
                    PacketWrapper[] packetWrapperArray2 = packetWrapperArray;
                    if (-7210432490371770710L < l) {
                        packetWrapperArray2 = packetWrapperArray;
                    }
                    if (!optional.isPresent()) {
                        return Optional.of(new SlotPos(n, n2));
                    }
                    ++n2;
                }
                break;
            }
            return Optional.empty();
        }

        @Override
        public Optional A(int n, long l, int n2) {
            int n3 = n;
            Impl impl = this;
            if (l >= 1L) {
                if (n3 >= impl.clickableItems.length) {
                    return Optional.empty();
                }
                n3 = n2;
                impl = this;
            }
            if (n3 >= impl.clickableItems[n].length) {
                return Optional.empty();
            }
            return Optional.ofNullable(this.clickableItems[n][n2]);
        }

        @Override
        public Optional E(int n, int n2, SlotPos slotPos, int n3) {
            long l = (long)n << 32 | (long)n2 << 56 >>> 32 | (long)n3 << 40 >>> 40;
            long l2 = l ^ 0x2231D80C6434L;
            return this.A(slotPos.Np(), l2, slotPos.N3());
        }

        @Override
        public InventoryContents y(int n, int n2, ClickableItem clickableItem) {
            if (n >= this.clickableItems.length) {
                return this;
            }
            if (n2 >= this.clickableItems[n].length) {
                return this;
            }
            this.clickableItems[n][n2] = clickableItem;
            this.update(n, n2, clickableItem != null ? clickableItem.getItem() : null);
            return this;
        }

        @Override
        public InventoryContents C(SlotPos slotPos, ClickableItem clickableItem) {
            return this.y(slotPos.Np(), slotPos.N3(), clickableItem);
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public InventoryContents f(ClickableItem var1_1, long var2_2) {
            var10_3 = 0;
            var9_4 = wz.getPacketWrappers();
            block0: while (var10_3 < this.clickableItems.length) {
                block1: for (var11_5 = 0; var11_5 < this.clickableItems[0].length; ++var11_5) {
                    while (true) {
                        v0 = var9_4;
                        if (var2_2 < 0L) continue block0;
                        if (this.clickableItems[var10_3][var11_5] != null) continue block1;
                        this.y(var10_3, var11_5, var1_1);
                        return this;
                    }
                }
                if (var2_2 < 1L) ** continue;
                ++var10_3;
                v0 = var9_4;
            }
            return this;
        }

        @Override
        public InventoryContents h(ClickableItem clickableItem, long l) {
            int n = 0;
            PacketWrapper[] packetWrapperArray = wz.getPacketWrappers();
            block0: while (n < this.clickableItems.length) {
                PacketWrapper[] packetWrapperArray2;
                for (int i = 0; i < this.clickableItems[n].length; ++i) {
                    this.y(n, i, clickableItem);
                    packetWrapperArray2 = packetWrapperArray;
                    if (1L >= l) continue block0;
                }
                ++n;
                packetWrapperArray2 = packetWrapperArray;
            }
            return this;
        }

        @Override
        public InventoryContents c(int n, long l, ClickableItem clickableItem) {
            if (n >= this.clickableItems.length) {
                return this;
            }
            for (int i = 0; i < this.clickableItems[n].length; ++i) {
                InventoryContents inventoryContents = this;
                if (l <= 1L) continue;
                inventoryContents = inventoryContents.y(n, i, clickableItem);
            }
            return this;
        }

        @Override
        public InventoryContents b(int n, ClickableItem clickableItem, long l) {
            for (int i = 0; i < this.clickableItems.length; ++i) {
                InventoryContents inventoryContents = this;
                if (0L > l) continue;
                inventoryContents = inventoryContents.y(i, n, clickableItem);
            }
            return this;
        }

        @Override
        public InventoryContents Z(long l, ClickableItem clickableItem) {
            long l2 = l ^ 0x765260D01562L;
            this.D(0, l2, 0, this.smartInventory.Pr() - 1, this.smartInventory.PH() - 1, clickableItem);
            return this;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public InventoryContents D(int var1_1, long var2_2, int var4_3, int var5_4, int var6_5, ClickableItem var7_6) {
            var18_7 = var1_1;
            var17_8 = wz.getPacketWrappers();
            block0: while (var18_7 <= var5_4) {
                var19_9 = var4_3;
                block1: while (var19_9 <= var6_5) {
                    while (true) {
                        block6: {
                            block5: {
                                v0 = var17_8;
                                if (-7876305164402785966L >= var2_2) continue block0;
                                v1 = var18_7;
                                if (0L > var2_2) break block5;
                                if (v1 == var1_1) ** GOTO lbl-1000
                                v1 = var18_7;
                            }
                            v2 = var17_8;
                            if (var2_2 <= -3420244829004031404L) break block6;
                            if (v1 == var5_4) ** GOTO lbl-1000
                            v1 = var19_9;
                            v2 = var17_8;
                        }
                        if (v1 != var4_3 && var19_9 != var6_5) {
                            v3 = var17_8;
                            if (var2_2 < 1L) continue block1;
                        } else lbl-1000:
                        // 3 sources

                        {
                            this.y(var18_7, var19_9, var7_6);
                        }
                        ++var19_9;
                        v3 = var17_8;
                        continue block1;
                        break;
                    }
                }
                if (var2_2 < 1L) ** continue;
                ++var18_7;
                v0 = var17_8;
            }
            return this;
        }

        @Override
        public InventoryContents e(SlotPos slotPos, SlotPos slotPos2, long l, ClickableItem clickableItem) {
            long l2 = l ^ 0xAE0895B83C9L;
            return this.D(slotPos.Np(), l2, slotPos.N3(), slotPos2.Np(), slotPos2.N3(), clickableItem);
        }

        @Override
        public Object V(Object object) {
            return this.l.get(object);
        }

        @Override
        public Object w(long l, String string, Object object) {
            return this.l.containsKey(string) ? this.l.get(string) : object;
        }

        @Override
        public InventoryContents J(Object object, Object object2) {
            this.l.put((String)object, object2);
            return this;
        }

        private void update(int n, int n2, ItemStack itemStack) {
            if (!this.smartInventory.getInventoryManager().getOpenedPlayers(this.smartInventory).contains(this.player)) {
                return;
            }
            Inventory inventory = this.player.getOpenInventory().getTopInventory();
            inventory.setItem(this.smartInventory.PH() * n + n2, itemStack);
        }

        private static we a(we we2) {
            return we2;
        }
    }
}

