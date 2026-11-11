/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.api.check.abstraction;

import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import java.util.function.BiFunction;

public interface CheckFunction
extends BiFunction<ProtocolPlayer, CheckOptions, Check<?>> {
}

