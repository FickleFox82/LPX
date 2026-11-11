/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.tab;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;
import java.util.ArrayList;
import java.util.List;

public class TabB
extends PlayerCheck<WrapperPlayClientTabComplete> {
    private static final String[] DEFAULT_CHARACTERS = new String[]{"[", "]", "{", "}", "@", "=", "nbt"};
    private List<String> characters = new ArrayList<String>();
    private int maxLength;

    public TabB(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.maxLength = this.getInt("max-length", 256);
        this.characters = this.getStringList("characters", DEFAULT_CHARACTERS);
    }

    protected CheckResult ca(Object object, WrapperPlayClientTabComplete wrapperPlayClientTabComplete) {
        String string = wrapperPlayClientTabComplete.getText();
        int n = 81;
        int n2 = string.length();
        if (n2 > this.maxLength) {
            return CheckResult.DENY;
        }
        for (String string2 : this.characters) {
            if (string.contains(string2)) {
                this.Fn(wrapperPlayClientTabComplete, new FlagInfo[]{FlagInfo.of("text", string), FlagInfo.of("invalid-char", string2)});
                return CheckResult.DENY;
            }
            if (n != 0) continue;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

