/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 */
package it.ytnoos.lpx.utils;

import com.github.retrooper.packetevents.wrapper.e1;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class BlockStateHandler {
    private static final Map<Class<?>, Method> METHOD_CACHE = new ConcurrentHashMap();
    private static final String GET_STATE = "getState";

    public static BlockState getBlockState(Block block) {
        try {
            Method method = METHOD_CACHE.computeIfAbsent(block.getClass(), clazz -> {
                try {
                    Class[] classArray = new Class[]{Boolean.TYPE};
                    Class clazz2 = clazz;
                    return clazz2.getMethod(e1.b(GET_STATE, clazz2, classArray), classArray);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    return null;
                }
            });
            if (method != null) {
                return (BlockState)method.invoke(block, false);
            }
        }
        catch (Exception exception) {}
        return block.getState();
    }
}

