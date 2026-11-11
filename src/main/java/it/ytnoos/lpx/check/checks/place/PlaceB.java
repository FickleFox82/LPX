/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.place;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateSign;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.nio.charset.StandardCharsets;

public class PlaceB
extends PlayerCheck<WrapperPlayClientUpdateSign> {
    private double max;

    public PlaceB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.max = this.getDouble("max", 70.0);
    }

    protected CheckResult yA(Object object, WrapperPlayClientUpdateSign wrapperPlayClientUpdateSign) {
        for (String string : wrapperPlayClientUpdateSign.getTextLines()) {
            long l = string.getBytes(StandardCharsets.UTF_8).length;
            if (!((double)l > this.max)) continue;
            this.Fn(wrapperPlayClientUpdateSign, new FlagInfo[]{FlagInfo.of("line", string), FlagInfo.of("line-size", l)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

