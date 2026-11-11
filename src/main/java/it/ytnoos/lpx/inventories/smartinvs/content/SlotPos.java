/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.inventories.smartinvs.content;

import it.ytnoos.lpx.we;

public class SlotPos {
    private final int L;
    private final int D;
    private final String x = "ed4e41953ef842b13d921984d0cf4800";

    public SlotPos(int n, int n2) {
        this.L = n;
        this.D = n2;
    }

    public static SlotPos _o(int n, int n2) {
        return new SlotPos(n, n2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        SlotPos slotPos = (SlotPos)object;
        return this.L == slotPos.L && this.D == slotPos.D;
    }

    public int hashCode() {
        int n = this.L;
        n = 31 * n + this.D;
        return n;
    }

    public int Np() {
        return this.L;
    }

    public int N3() {
        return this.D;
    }

    private static we a(we we2) {
        return we2;
    }
}

