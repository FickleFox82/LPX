/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package it.ytnoos.lpx.inventories.smartinvs.content;

import com.google.common.collect.Sets;
import it.ytnoos.lpx._c;
import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;
import it.ytnoos.lpx.inventories.smartinvs.content.SlotPos;
import it.ytnoos.lpx.we;
import it.ytnoos.lpx.wz;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.Set;

public interface SlotIterator {
    public Optional S(long var1);

    public SlotIterator H(ClickableItem var1);

    public SlotIterator u(long var1);

    public SlotIterator n();

    public SlotIterator x(int var1, int var2);

    public SlotIterator W(SlotPos var1);

    public int k();

    public SlotIterator l(int var1);

    public int g();

    public SlotIterator K(int var1);

    public boolean Y();

    public boolean L();

    public boolean d();

    public SlotIterator z(boolean var1);

    public static class Impl
    implements SlotIterator {
        private final InventoryContents inventoryContents;
        private final SmartInventory smartInventory;
        private final wz K;
        private final Set<SlotPos> blacklisted = Sets.newHashSet();
        private boolean C = false;
        private boolean q = true;
        private int B;
        private int v;
        private static final long a = _c.a(-8514777243312620562L, 7495656129711808872L, MethodHandles.lookup().lookupClass()).a(115693522996610L);

        public Impl(InventoryContents inventoryContents, SmartInventory smartInventory, wz wz2, int n, int n2) {
            this.inventoryContents = inventoryContents;
            this.smartInventory = smartInventory;
            this.K = wz2;
            this.B = n;
            this.v = n2;
        }

        public Impl(InventoryContents inventoryContents, SmartInventory smartInventory, wz wz2) {
            this(inventoryContents, smartInventory, wz2, 0, 0);
        }

        @Override
        public Optional S(long l) {
            long l2 = l ^ 0x1D5208758544L;
            return this.inventoryContents.A(this.B, l2, this.v);
        }

        @Override
        public SlotIterator H(ClickableItem clickableItem) {
            block0: {
                long l = 10955514806272L;
                long l2 = l ^ 0x6519D90F771AL;
                Object[] objectArray = new Object[1];
                objectArray[0] = l2;
                if (!this.fn(objectArray)) break block0;
                this.inventoryContents.y(this.B, this.v, clickableItem);
            }
            return this;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public SlotIterator u(long var1_1) {
            block11: {
                var5_2 = var1_1 ^ 91210411773655L;
                var7_3 = wz.getPacketWrappers();
                if (this.B != 0) break block11;
                v0 = this;
                if (1L <= var1_1) {
                    if (v0.v == 0) {
                        this.C = true;
                        return this;
                    } else {
                        ** GOTO lbl11
                    }
                }
                ** GOTO lbl14
            }
            block4: while (true) {
                block12: {
                    v0 = this;
lbl14:
                    // 2 sources

                    if (v0.C) break block12;
                    this.C = true;
                    ** GOTO lbl-1000
                }
                do {
                    switch (this.K.ordinal()) {
                        case 0: {
                            --this.v;
                            if (this.v == 0) {
                                this.v = this.smartInventory.PH() - 1;
                                --this.B;
                            }
                            ** GOTO lbl33
                        }
                        case 1: {
                            v1 = this;
                            if (1L >= var1_1) break;
                            --v1.B;
                            if (this.B == 0) {
                                this.B = this.smartInventory.Pr() - 1;
                                --this.v;
                            }
                        }
lbl33:
                        // 5 sources

                        default: lbl-1000:
                        // 2 sources

                        {
                            v1 = this;
                        }
                    }
                    v2 = var7_3;
                    if (-1991644226447607469L <= var1_1) {
                        v3 = new Object[1];
                        v2 = v3;
                        v3[0] = var5_2;
                    }
                    if (!v1.fn(v2) && (this.B != 0 || this.v != 0)) continue block4;
                } while (1L >= var1_1);
                break;
            }
            return this;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public SlotIterator n() {
            block10: {
                var10_1 = 31262812372841L;
                var3_2 = var10_1 ^ 123703351150707L;
                var7_3 = wz.getPacketWrappers();
                if (this.L()) {
                    this.C = true;
                    return this;
                }
                block4: while (true) {
                    block11: {
                        if (this.C) break block11;
                        this.C = true;
                        ** GOTO lbl-1000
                    }
                    do {
                        switch (this.K.ordinal()) {
                            case 0: {
                                ++this.v;
                                this.v %= this.smartInventory.PH();
                                if (this.v == 0) {
                                    ++this.B;
                                }
                                ** GOTO lbl27
                            }
                            case 1: {
                                v0 = this;
                                if (-50606855931998043L > var10_1) break;
                                v0.B = ++this.B % this.smartInventory.Pr();
                                if (this.B == 0) {
                                    ++this.v;
                                }
                            }
lbl27:
                            // 5 sources

                            default: lbl-1000:
                            // 2 sources

                            {
                                v0 = this;
                            }
                        }
                        v1 = var7_3;
                        if (var10_1 >= 1L) {
                            v2 = new Object[1];
                            v1 = v2;
                            v2[0] = var3_2;
                        }
                        if (v0.fn(v1)) break block10;
                        if (!this.L()) continue block4;
                    } while (1L > var10_1);
                    break;
                }
            }
            return this;
        }

        @Override
        public SlotIterator x(int n, int n2) {
            this.blacklisted.add(SlotPos._o(n, n2));
            return this;
        }

        @Override
        public SlotIterator W(SlotPos slotPos) {
            return this.x(slotPos.Np(), slotPos.N3());
        }

        @Override
        public int k() {
            return this.B;
        }

        @Override
        public SlotIterator l(int n) {
            this.B = n;
            return this;
        }

        @Override
        public int g() {
            return this.v;
        }

        @Override
        public SlotIterator K(int n) {
            this.v = n;
            return this;
        }

        @Override
        public boolean Y() {
            return this.C;
        }

        @Override
        public boolean L() {
            long l = 1809869833049L;
            int n = this.smartInventory.Pr();
            if (l > 0L) {
                n = n - 1;
            }
            return this.B == n && this.v == this.smartInventory.PH() - 1;
        }

        @Override
        public boolean d() {
            return this.q;
        }

        @Override
        public SlotIterator z(boolean bl) {
            this.q = bl;
            return this;
        }

        private boolean fn(Object[] objectArray) {
            long l = (Long)objectArray[0];
            long l2 = (l = a ^ l) ^ 0x5622D6D55B19L;
            return !this.blacklisted.contains(SlotPos._o(this.B, this.v)) && (this.q || !this.S(l2).isPresent());
        }

        private static we a(we we2) {
            return we2;
        }
    }
}

