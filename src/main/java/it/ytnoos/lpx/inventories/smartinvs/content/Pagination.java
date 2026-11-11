/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.inventories.smartinvs.content;

import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.content.SlotIterator;
import it.ytnoos.lpx.we;
import java.util.Arrays;

public interface Pagination {
    public ClickableItem[] s();

    public int getPage();

    public Pagination a(int var1);

    public boolean R();

    public boolean p();

    public Pagination M();

    public Pagination t();

    public Pagination q();

    public Pagination P();

    public Pagination B(SlotIterator var1);

    public Pagination v(ClickableItem[] var1);

    public Pagination N(int var1);

    public static class Impl
    implements Pagination {
        private int Z;
        private ClickableItem[] clickableItems = new ClickableItem[0];
        private int K = 5;

        @Override
        public ClickableItem[] s() {
            return Arrays.copyOfRange(this.clickableItems, this.Z * this.K, (this.Z + 1) * this.K);
        }

        @Override
        public int getPage() {
            return this.Z;
        }

        @Override
        public Pagination a(int n) {
            this.Z = n;
            return this;
        }

        @Override
        public boolean R() {
            return this.Z == 0;
        }

        @Override
        public boolean p() {
            int n = (int)Math.ceil((double)this.clickableItems.length / (double)this.K);
            return this.Z >= n - 1;
        }

        @Override
        public Pagination M() {
            this.Z = 0;
            return this;
        }

        @Override
        public Pagination t() {
            block0: {
                if (this.R()) break block0;
                --this.Z;
            }
            return this;
        }

        @Override
        public Pagination q() {
            block0: {
                if (this.p()) break block0;
                ++this.Z;
            }
            return this;
        }

        @Override
        public Pagination P() {
            this.Z = this.clickableItems.length / this.K;
            return this;
        }

        @Override
        public Pagination B(SlotIterator slotIterator) {
            for (ClickableItem clickableItem : this.s()) {
                slotIterator.n().H(clickableItem);
                if (slotIterator.L()) break;
            }
            return this;
        }

        @Override
        public Pagination v(ClickableItem[] clickableItemArray) {
            this.clickableItems = clickableItemArray;
            return this;
        }

        @Override
        public Pagination N(int n) {
            this.K = n;
            return this;
        }

        private static we a(we we2) {
            return we2;
        }
    }
}

