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
import it.ytnoos.lpx.utils.string.StringUtils;
import it.ytnoos.lpx.we;
import java.util.List;
import java.util.function.Predicate;

public class TabA
extends PlayerCheck<WrapperPlayClientTabComplete> {
    private static final String[] h = new String[]{"/to ", "//to", "/calc ", "//calc"};
    private static final String[] t = new String[]{"targetoffset", "for(", "^(.", "*."};
    private List<String> starts;
    private List<String> contains;

    public TabA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.starts = this.getStringList("starts", h);
        this.contains = this.getStringList("contains", t);
    }

    protected CheckResult check(Object object, WrapperPlayClientTabComplete wrapperPlayClientTabComplete) {
        String string = wrapperPlayClientTabComplete.getText();
        boolean bl = this.findWord(this.starts, string2 -> StringUtils.J0(string, string2));
        if (!bl) {
            bl = this.findWord(this.contains, string2 -> StringUtils.JA(string, string2));
        }
        if (bl) {
            this.Fn(wrapperPlayClientTabComplete, new FlagInfo[]{FlagInfo.of("text", string)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private boolean findWord(List list, Predicate predicate) {
        for (String string : list) {
            if (!predicate.test(string)) continue;
            return true;
        }
        return false;
    }

    private static we a(we we2) {
        return we2;
    }
}

