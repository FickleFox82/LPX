/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package it.ytnoos.lpx.api.check.abstraction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.RegisteredCheck;
import it.ytnoos.lpx.api.check.abstraction.CheckFunction;
import it.ytnoos.lpx.api.check.enums.CheckType;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;
import it.ytnoos.lpx.we;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CheckManager {
    public static final CheckContainer<?> EMPTY_CONTAINER = new CheckContainer();
    private final LPXPlugin plugin;
    private final Map<Object, List<RegisteredCheck<?>>> checks = Maps.newHashMap();

    protected CheckManager(LPXPlugin lPXPlugin) {
        this.plugin = lPXPlugin;
    }

    public void register(Object object2, CheckFunction checkFunction, CheckType checkType) {
        this.checks.computeIfAbsent(object2, object -> Lists.newArrayList()).add(new RegisteredCheck(checkType, new CheckOptions(this.plugin, checkType), checkFunction));
    }

    public void sortByPriority() {
        for (List<RegisteredCheck<?>> list : this.checks.values()) {
            list.sort(Comparator.comparingInt(registeredCheck -> registeredCheck.getType().getPriority()));
        }
    }

    public Map<Object, CheckContainer<?>> createPlayerChecks(ProtocolPlayer protocolPlayer) {
        HashMap hashMap = Maps.newHashMap();
        this.checks.forEach((object, list) -> {
            CheckContainer checkContainer = new CheckContainer();
            for (RegisteredCheck registeredCheck : list) {
                checkContainer.add(registeredCheck.getFunction().apply(protocolPlayer, registeredCheck.getOptions()));
            }
            hashMap.put(object, checkContainer);
        });
        return hashMap;
    }

    public Map<Object, List<RegisteredCheck<?>>> getChecks() {
        return this.checks;
    }

    public abstract void registerChecks();

    private static we a(we we2) {
        return we2;
    }
}

