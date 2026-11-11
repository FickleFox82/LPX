/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.payload;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class PayloadA
extends PlayerCheck<WrapperPlayClientPluginMessage> {
    public PayloadA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult J0(Object object, WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        if (wrapperPlayClientPluginMessage.getChannelName() == null || wrapperPlayClientPluginMessage.getChannelName().isEmpty()) {
            this.Fn(wrapperPlayClientPluginMessage, new FlagInfo[0]);
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

