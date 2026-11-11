/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.inventories.smartinvs;

import java.util.function.Consumer;

public class InventoryListener<T> {
    private final Class<T> type;
    private final Consumer<T> consumer;

    public InventoryListener(Class<T> clazz, Consumer<T> consumer) {
        this.type = clazz;
        this.consumer = consumer;
    }

    public void accept(Object object) {
        this.consumer.accept(object);
    }

    public Class getType() {
        return this.type;
    }
}

