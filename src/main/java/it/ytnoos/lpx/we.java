/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx;

final class we
extends RuntimeException {
    private we(RuntimeException runtimeException) {
        super(runtimeException.getMessage(), runtimeException, false, false);
    }

    public synchronized RuntimeException zh() {
        return (RuntimeException)super.getCause();
    }
}

